package dev.juanrincon.trips.presentation.destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.utils.toUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DestinationViewModel(
    private val tripId: Int,
    private val repository: TripRepository
) : ViewModel(),
    MVI<DestinationState, DestinationIntent, DestinationEvent> by mviHandler(DestinationState()) {

    init {
        getTripAndItems()
    }

    override fun onIntent(intent: DestinationIntent) = when (intent) {
        is DestinationIntent.CancelTrip -> cancelTrip(intent.tripId)
    }

    private fun cancelTrip(tripId: Int) {
        viewModelScope.launch {
            repository.deleteTripAndItems(tripId).onSuccess {
                playClosingAnimation()
                emitSideEffect(DestinationEvent.CancelTrip)
            }
        }
    }

    private fun getTripAndItems() {
        repository.getCurrentTrip().combine(repository.getItemsForTrip(tripId)) { trip, items ->
            trip?.let {
                UITrip(
                    it.id,
                    it.name,
                    it.status.toUIModel(),
                    it.current,
                    items.map { item -> item.toUIModel() })
            }
        }.onStart { updateState { copy(loading = true) } }
            .onCompletion { updateState { copy(loading = false) } }.onEach { trip ->
                trip?.let {
                    delay(50)
                    updateState {
                        copy(
                            trip = it,
                            loading = false,
                            transitionAnimation = true,
                            isNextButtonEnabled = it.items.sumOf { item -> item.total } > 0
                        )
                    }
                    delay(100)
                    updateState { copy(listAnimation = true) }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun playClosingAnimation() {
        updateState { copy(listAnimation = false) }
        delay(125)
        updateState { copy(transitionAnimation = false) }
    }
}