package dev.juanrincon.respite.presentation.trips

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.model.TripStatus
import dev.juanrincon.respite.domain.repository.TripItemRepository
import dev.juanrincon.respite.domain.repository.TripRepository
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.MVIDelegate
import kotlinx.coroutines.launch

class TripScreenModel(
    private val tripRepository: TripRepository,
    private val tripItemRepository: TripItemRepository
) : ScreenModel, MVI<TripState, TripIntent, Nothing> by MVIDelegate(TripState()) {

    init {
        getTripAndItems()
    }

    private fun getTripAndItems() {
        updateState { copy(loading = true) }
        screenModelScope.launch {
            tripRepository.readCurrentTrip().onSuccess { trip ->
                trip?.let {
                    if (trip.status is TripStatus.PackingDestination) {
                        tripItemRepository.readAll(trip.id).onSuccess { items ->
                            updateState { copy(trip = trip, items = items, loading = false) }
                        }.onFailure {
                            updateState { copy(loading = false) }
                        }
                    } else {
                        tripItemRepository.readCurrentTrip(trip.id).onSuccess { items ->
                            updateState { copy(trip = trip, items = items, loading = false) }
                        }.onFailure {
                            updateState { copy(loading = false) }
                        }
                    }
                }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }
}