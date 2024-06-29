package dev.juanrincon.trips.presentation.empty_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler
import dev.juanrincon.trips.domain.TripRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
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
            tripRepository.getCurrentTrip()
                .onStart { updateState { copy(loading = false) } }
                .firstOrNull { it != null }?.let { trip ->
                    when (trip.status) {
                        TripStatus.Destination -> emitSideEffect(EmptyScreenEvent.Destination(trip.id))
                        TripStatus.PackingDestination -> emitSideEffect(
                            EmptyScreenEvent.PackForDestination(
                                trip.id
                            )
                        )

                        TripStatus.PackingNextDestination -> emitSideEffect(
                            EmptyScreenEvent.PackForNextDestination(
                                trip.id
                            )
                        )
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