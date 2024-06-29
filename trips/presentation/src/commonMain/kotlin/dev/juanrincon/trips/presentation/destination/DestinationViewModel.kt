package dev.juanrincon.trips.presentation.destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITrip.Companion.toNextDestination
import dev.juanrincon.trips.presentation.utils.toDomainModel
import dev.juanrincon.trips.presentation.utils.toUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DestinationViewModel(
    private val tripId: Int,
    private val repository: TripRepository
) : ViewModel(),
    MVI<DestinationState, DestinationIntent, DestinationEvent> by mviHandler(DestinationState()) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: DestinationIntent) = when (intent) {
        is DestinationIntent.CancelTrip -> cancelTrip(intent.tripId)
        is DestinationIntent.StartPacking -> startPacking(intent.trip)
    }

    private fun cancelTrip(tripId: Int) {
        viewModelScope.launch {
            repository.deleteTripAndItems(tripId).onSuccess {
                playClosingAnimation()
                emitSideEffect(DestinationEvent.CancelTrip)
            }
        }
    }

    private fun startPacking(trip: UITrip) {
        viewModelScope.launch {
            repository.updateTrip(trip.toNextDestination().toDomainModel()).onSuccess {
                playClosingAnimation()
                emitSideEffect(DestinationEvent.PackForNextDestination(trip.id))
            }
        }
    }

    private fun getTripAndItems() {
        repository.getTripAndItems(tripId).map { trip -> trip?.toUIModel() }
            .onEach { trip ->
                trip?.let {
                    delay(50)
                    updateState {
                        copy(
                            trip = it,
                            loading = false,
                            transitionAnimation = true,
                        )
                    }
                    delay(100)
                    updateState { copy(listAnimation = true) }
                }
            }.onStart { updateState { copy(loading = true) } }
            .onCompletion { updateState { copy(loading = false) } }.launchIn(viewModelScope)
    }

    private suspend fun playClosingAnimation() {
        updateState { copy(listAnimation = false) }
        delay(125)
        updateState { copy(transitionAnimation = false) }
    }
}