package dev.juanrincon.trips.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.MVIDelegate
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.models.TripIntent
import dev.juanrincon.trips.presentation.models.TripState
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.models.UITripStatus
import dev.juanrincon.trips.presentation.utils.toDomainModel
import dev.juanrincon.trips.presentation.utils.toUIModel
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

    private fun finishPacking(trip: UITrip) {
        if (trip.status is UITripStatus.PackingDestination) {
            screenModelScope.launch {
                tripRepository.updateTrip(trip.toDomainModel())
                getTripAndItems()
            }
        }
    }

    private fun cancelPacking(tripId: Int, currentStatus: UITripStatus) {
        if (currentStatus is UITripStatus.PackingDestination) {
            screenModelScope.launch {
                tripRepository.deleteTripAndItems(tripId)
                getTripAndItems()
            }
        }
    }

    private fun incrementItemCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        screenModelScope.launch {
            tripRepository.upsertItem(tripId, item.toDomainModel().increment()).onSuccess {
                getTripAndItems()
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }

    private fun decrementItemCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        val newItem = item.toDomainModel().decrement()
        screenModelScope.launch {
            if (newItem.total == 0) {
                tripRepository.deleteTripItem(tripId, newItem.id).onSuccess {
                    getTripAndItems()
                }.onFailure {
                    updateState { copy(loading = false) }
                }
            } else if (newItem.total < 0) {
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
                updateState { copy(trip = trip?.toUIModel(), loading = false) }
            }.onFailure {
                updateState { copy(loading = false) }
            }
        }
    }
}