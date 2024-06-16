package dev.juanrincon.trips.presentation.empty_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler
import dev.juanrincon.trips.domain.TripRepository
import kotlinx.coroutines.launch

class EmptyViewModel(
    private val tripRepository: TripRepository
) : ViewModel(),
    MVI<EmptyScreenState, EmptyScreenIntent, EmptyScreenEvent> by mviHandler(EmptyScreenState()) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: EmptyScreenIntent) = when (intent) {
        is EmptyScreenIntent.CreateTrip -> createTrip(intent.name)
        EmptyScreenIntent.NavigateToCategories -> Unit
        EmptyScreenIntent.NavigateToLuggage -> Unit
        EmptyScreenIntent.NavigateToPackForDestination -> Unit
        EmptyScreenIntent.GetTrips -> getTripAndItems()
    }

    private fun getTripAndItems() {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            tripRepository.getCurrentTrip().collect { trip ->
                updateState { copy(loading = false) }
                trip?.let {
                    when (it.status) {
                        TripStatus.Destination -> emitSideEffect(EmptyScreenEvent.Destination(trip.id))
                        TripStatus.PackingDestination -> emitSideEffect(
                            EmptyScreenEvent.PackForDestination(
                                trip.id
                            )
                        )

                        TripStatus.PackingNextDestination -> emitSideEffect(EmptyScreenEvent.PackForNextDestination)
                    }
                }
            }
        }
    }

    private fun createTrip(name: String) {
        viewModelScope.launch {
            tripRepository.createTrip(name)
        }
    }
}