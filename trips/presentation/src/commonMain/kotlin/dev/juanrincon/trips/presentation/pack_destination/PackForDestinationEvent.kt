package dev.juanrincon.trips.presentation.pack_destination

sealed interface PackForDestinationEvent {
    data object CancelPacking : PackForDestinationEvent
    data class PackForDestination(val tripId: Int) : PackForDestinationEvent
}