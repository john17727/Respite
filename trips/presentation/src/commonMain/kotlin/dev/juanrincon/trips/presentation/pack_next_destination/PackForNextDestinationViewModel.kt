package dev.juanrincon.trips.presentation.pack_next_destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler
import dev.juanrincon.trips.domain.TripRepository
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

class PackForNextDestinationViewModel(
    private val tripId: Int,
    private val repository: TripRepository
) : ViewModel(),
    MVI<PackForNextDestinationState, PackForNextDestinationIntent, PackForNextDestinationEvent> by mviHandler(
        PackForNextDestinationState()
    ) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: PackForNextDestinationIntent) = when (intent) {
        is PackForNextDestinationIntent.CancelPacking -> cancelPacking(intent.tripId)
        is PackForNextDestinationIntent.FinishPacking -> finishPacking(intent.tripId)
        is PackForNextDestinationIntent.PackItem -> incrementPackedCount(intent.tripId, intent.item)
        is PackForNextDestinationIntent.UnpackItem -> decrementPackedCount(
            intent.tripId,
            intent.item
        )
    }

    private fun decrementPackedCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            repository.upsertItem(tripId, item.toDomainModel().decrementPacked()).onSuccess {
                updateState { copy(loading = false) }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun incrementPackedCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            repository.upsertItem(tripId, item.toDomainModel().incrementPacked()).onSuccess {
                updateState { copy(loading = false) }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun finishPacking(tripId: Int) {
        viewModelScope.launch {
            repository.deleteTripAndItems(tripId).onSuccess {
                playClosingAnimation()
                emitSideEffect(PackForNextDestinationEvent.FinishTrip)
            }
        }
    }

    private fun cancelPacking(tripId: Int) {
        viewModelScope.launch {
            repository.deleteTripAndItems(tripId).onSuccess {
                playClosingAnimation()
                emitSideEffect(PackForNextDestinationEvent.CancelPacking)
            }
        }
    }

    private fun getTripAndItems() {
        repository.getTripAndItems(tripId)
            .onStart { updateState { copy(loading = true) } }
            .onCompletion { updateState { copy(loading = false) } }
            .map { trip -> trip?.toUIModel() }
            .onEach { trip ->
                trip?.let {
                    delay(50)
                    updateState {
                        copy(
                            trip = it,
                            transitionAnimation = true,
                            isNextButtonEnabled = trip.items.sumOf { item -> item.total } == trip.items.sumOf { item -> item.packed }
                        )
                    }
                    delay(100)
                    updateState { copy(listAnimation = true) }
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun playClosingAnimation() {
        updateState { copy(listAnimation = false) }
        delay(125)
        updateState { copy(transitionAnimation = false) }
    }
}