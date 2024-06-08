package dev.juanrincon.trips.presentation.destination

import androidx.lifecycle.ViewModel
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mvi
import dev.juanrincon.trips.domain.TripRepository

class DestinationViewModel(
    private val repository: TripRepository
) : ViewModel(),
    MVI<DestinationState, DestinationIntent, DestinationEvent> by mvi(DestinationState()) {
}