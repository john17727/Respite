package dev.juanrincon.trips.presentation.empty_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mvi
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.utils.toUIModel
import kotlinx.coroutines.launch

class EmptyViewModel(
    private val tripRepository: TripRepository
) : ViewModel(), MVI<EmptyScreenState, EmptyScreenIntent, Nothing> by mvi(EmptyScreenState()) {
    init {
        getTripAndItems()
    }

    override fun onIntent(intent: EmptyScreenIntent) = when (intent) {
        is EmptyScreenIntent.CreateTrip -> createTrip(intent.name)
    }

    private fun getTripAndItems() {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            tripRepository.getCurrentTrip().onSuccess { trip ->
                updateState { copy(trip = trip?.toUIModel(), loading = false) }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun createTrip(name: String) {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            tripRepository.createTrip(name).onSuccess {
                getTripAndItems()
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }
}