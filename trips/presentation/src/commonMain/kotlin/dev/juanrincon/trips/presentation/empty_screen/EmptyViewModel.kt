package dev.juanrincon.trips.presentation.empty_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mvi
import dev.juanrincon.trips.domain.TripRepository
import kotlinx.coroutines.launch

class EmptyViewModel(
    private val tripRepository: TripRepository
) : ViewModel(),
    MVI<EmptyScreenState, EmptyScreenIntent, EmptyScreenEvent> by mvi(EmptyScreenState()) {

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
            tripRepository.getCurrentTrip().onSuccess { trip ->
                when (trip?.status) {
                    TripStatus.Destination -> emitSideEffect(EmptyScreenEvent.Destination)
                    TripStatus.PackingDestination -> emitSideEffect(EmptyScreenEvent.PackForDestination)
                    TripStatus.PackingNextDestination -> emitSideEffect(EmptyScreenEvent.PackForNextDestination)
                    null -> updateState { copy(loading = false) }
                }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun createTrip(name: String) {
        viewModelScope.launch {
            tripRepository.createTrip(name).onSuccess {
                emitSideEffect(EmptyScreenEvent.TripCreationSuccess)
            }
        }
    }
}