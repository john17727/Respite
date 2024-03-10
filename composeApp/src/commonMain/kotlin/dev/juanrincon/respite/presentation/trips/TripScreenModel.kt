package dev.juanrincon.respite.presentation.trips

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.model.TripItem
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

    override fun onIntent(intent: TripIntent) = when (intent) {
        TripIntent.StartCreateTrip -> updateState { copy(createNewTrip = true) }
        is TripIntent.CreateTrip -> createTrip(intent.name)
        is TripIntent.AddItem -> incrementItemCount(intent.tripId, intent.item)
        is TripIntent.RemoveItem -> decrementItemCount(intent.tripId, intent.item)
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