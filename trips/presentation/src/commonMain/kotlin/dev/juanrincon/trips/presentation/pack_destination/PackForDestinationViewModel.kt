package dev.juanrincon.trips.presentation.pack_destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.MVIDelegate
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.models.TripState
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.models.UITripStatus
import dev.juanrincon.trips.presentation.utils.toDomainModel
import dev.juanrincon.trips.presentation.utils.toUIModel
import kotlinx.coroutines.launch

class PackForDestinationViewModel(
    private val tripRepository: TripRepository,
) : ViewModel(), MVI<TripState, PackForDestinationIntent, Nothing> by MVIDelegate(TripState()) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: PackForDestinationIntent) = when (intent) {
        is PackForDestinationIntent.AddItem -> incrementItemCount(intent.tripId, intent.item)
        is PackForDestinationIntent.CancelPacking -> cancelPacking(intent.tripId, intent.tripStatus)
        is PackForDestinationIntent.FinishPacking -> finishPacking(intent.trip)
        is PackForDestinationIntent.RemoveItem -> decrementItemCount(intent.tripId, intent.item)
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

    private fun finishPacking(trip: UITrip) {
        if (trip.status is UITripStatus.PackingDestination) {
            viewModelScope.launch {
                tripRepository.updateTrip(trip.toDomainModel())
                getTripAndItems()
            }
        }
    }

    private fun cancelPacking(tripId: Int, currentStatus: UITripStatus) {
        if (currentStatus is UITripStatus.PackingDestination) {
            viewModelScope.launch {
                tripRepository.deleteTripAndItems(tripId)
                getTripAndItems()
            }
        }
    }

    private fun incrementItemCount(tripId: Int, item: UITripItem) {
        updateState { copy(loading = true) }
        viewModelScope.launch {
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
        viewModelScope.launch {
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
}