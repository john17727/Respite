package dev.juanrincon.trips.presentation.pack_destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.models.TripState
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITrip.Companion.toDestination
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.utils.toDomainModel
import dev.juanrincon.trips.presentation.utils.toUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PackForDestinationViewModel(
    private val tripId: Int,
    private val tripRepository: TripRepository,
) : ViewModel(),
    MVI<TripState, PackForDestinationIntent, PackForDestinationEvent> by mviHandler(TripState()) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: PackForDestinationIntent) = when (intent) {
        is PackForDestinationIntent.AddItem -> incrementItemCount(intent.tripId, intent.item)
        is PackForDestinationIntent.CancelPacking -> cancelPacking(intent.tripId)
        is PackForDestinationIntent.FinishPacking -> finishPacking(intent.trip)
        is PackForDestinationIntent.RemoveItem -> decrementItemCount(intent.tripId, intent.item)
    }

    private fun getTripAndItems() {

        tripRepository.getTripAndPotentialItems(tripId).map { trip -> trip?.toUIModel() }
            .onEach { trip ->
                trip?.let {
                    delay(50)
                    updateState {
                        copy(
                            trip = it,
                            loading = false,
                            transitionAnimation = true,
                            isNextButtonEnabled = trip.items.sumOf { item -> item.total } > 0
                        )
                    }
                    delay(100)
                    updateState { copy(listAnimation = true) }
                }
            }.onStart { updateState { copy(loading = true) } }
            .onCompletion { updateState { copy(loading = false) } }.launchIn(viewModelScope)
    }

    private fun finishPacking(trip: UITrip) {
        viewModelScope.launch {
            tripRepository.updateTrip(trip.toDestination().toDomainModel()).onSuccess {
                playClosingAnimation()
                emitSideEffect(PackForDestinationEvent.PackForDestination(trip.id))
            }
        }
    }

    private fun cancelPacking(tripId: Int) {
        viewModelScope.launch {
            tripRepository.deleteTripAndItems(tripId).onSuccess {
                playClosingAnimation()
                emitSideEffect(PackForDestinationEvent.CancelPacking)
            }
        }
    }

    private fun incrementItemCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            tripRepository.upsertItem(tripId, item.toDomainModel().increment()).onSuccess {
                updateState { copy(loading = false) }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun decrementItemCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        val newItem = item.toDomainModel().decrement()
        viewModelScope.launch {
            if (newItem.total == 0) {
                tripRepository.deleteTripItem(tripId, newItem.id).onSuccess {
                    updateState { copy(loading = false) }
                }.onFailure {
                    updateState { copy(loading = false) }
                }
            } else if (newItem.total < 0) {
                updateState { copy(loading = false) }
            } else {
                tripRepository.upsertItem(tripId, newItem).onSuccess {
                    updateState { copy(loading = false) }
                }.onFailure {
                    updateState { copy(loading = false) }
                }
            }
        }
    }

    private suspend fun playClosingAnimation() {
        updateState { copy(listAnimation = false) }
        delay(125)
        updateState { copy(transitionAnimation = false) }
    }
}