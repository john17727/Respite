package dev.juanrincon.trips.presentation.pack_next_destination

import androidx.lifecycle.ViewModel
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mviHandler

class PackForNextDestinationViewModel(
    private val tripId: Int
) : ViewModel(),
    MVI<PackForNextDestinationState, PackForNextDestinationIntent, PackForNextDestinationEvent> by mviHandler(
        PackForNextDestinationState()
    ) {
}