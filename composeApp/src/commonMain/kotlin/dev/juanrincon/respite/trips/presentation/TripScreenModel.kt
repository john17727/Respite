package dev.juanrincon.respite.trips.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.MVIDelegate
import dev.juanrincon.respite.trips.domain.Trip
import dev.juanrincon.respite.trips.domain.TripItem
import dev.juanrincon.respite.trips.domain.TripRepository
import dev.juanrincon.respite.trips.domain.TripStatus
import kotlinx.coroutines.launch

class TripScreenModel(
    private val tripRepository: TripRepository,
) : ScreenModel, MVI<TripState, TripIntent, Nothing> by MVIDelegate(TripState()) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: TripIntent) = when (intent) {
        is TripIntent.CreateTrip -> createTrip(intent.name)
        is TripIntent.AddItem -> incrementItemCount(intent.tripId, intent.item)
        is TripIntent.RemoveItem -> decrementItemCount(intent.tripId, intent.item)
        is TripIntent.CancelPacking -> cancelPacking(intent.tripId, intent.tripStatus)
        is TripIntent.FinishPacking -> finishPacking(intent.trip)
    }

    private fun finishPacking(trip: Trip) {
        if (trip.status is TripStatus.PackingDestination) {
            screenModelScope.launch {
                tripRepository.updateTrip(trip.copy(status = TripStatus.Destination))
                getTripAndItems()
            }
        }
    }

    private fun cancelPacking(tripId: Int, currentStatus: TripStatus) {
        if (currentStatus is TripStatus.PackingDestination) {
            screenModelScope.launch {
                tripRepository.deleteTripAndItems(tripId)
                getTripAndItems()
            }
        }
    }

    private fun incrementItemCount(tripId: Int, item: TripItem) {
        updateState { copy(loading = true) }
        screenModelScope.launch {
            tripRepository.upsertItem(tripId, item.increment()).onSuccess {
                getTripAndItems()
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun decrementItemCount(tripId: Int, item: TripItem) {
        updateState { copy(loading = true) }
        val newItem = item.decrement()
        screenModelScope.launch {
            if (newItem.amount == 0) {
                tripRepository.deleteTripItem(tripId, newItem.id).onSuccess {
                    getTripAndItems()
                }.onFailure {
                    updateState { copy(loading = false) }
                }
            } else if (newItem.amount < 0) {
                updateState { copy(loading = false) }
            } else {
                tripRepository.upsertItem(tripId, newItem).onSuccess {
                    getTripAndItems()
                }.onFailure {
                    updateState { copy(loading = false) }
                }
            }
        }
    }

    private fun createTrip(name: String) {
        updateState { copy(loading = true) }
        screenModelScope.launch {
            tripRepository.createTrip(name).onSuccess {
                getTripAndItems()
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
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