package dev.juanrincon.trips.presentation.destination

sealed interface DestinationEvent {
    data object CancelTrip : DestinationEvent
    data class PackForNextDestination(val tripId: Int) : DestinationEvent
}