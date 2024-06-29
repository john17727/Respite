package dev.juanrincon.trips.presentation.pack_next_destination

sealed interface PackForNextDestinationEvent {
    data object CancelPacking : PackForNextDestinationEvent
}