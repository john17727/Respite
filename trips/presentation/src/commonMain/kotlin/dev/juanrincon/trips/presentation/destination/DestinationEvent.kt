package dev.juanrincon.trips.presentation.destination

sealed interface DestinationEvent {
    data object CancelTrip : DestinationEvent
}