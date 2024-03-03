package dev.juanrincon.respite.presentation.trips

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.repository.TripRepository
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.MVIDelegate
import kotlinx.coroutines.launch

class TripScreenModel(
    private val tripRepository: TripRepository,
) : ScreenModel, MVI<TripState, TripIntent, Nothing> by MVIDelegate(TripState()) {

    init {
        getTripAndItems()
    }

    private fun getTripAndItems() {
        updateState { copy(loading = true) }
        screenModelScope.launch {
            tripRepository.getCurrentTrip().onSuccess { trip ->
                updateState { copy(trip = trip, loading = false) }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }
}