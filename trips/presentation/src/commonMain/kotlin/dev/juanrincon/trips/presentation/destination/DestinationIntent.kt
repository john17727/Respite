package dev.juanrincon.trips.presentation.destination

sealed interface DestinationIntent {
    data class CancelTrip(val tripId: Int) : DestinationIntent
}