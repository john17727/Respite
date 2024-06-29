package dev.juanrincon.trips.presentation.destination

import dev.juanrincon.trips.presentation.models.UITrip

sealed interface DestinationIntent {
    data class CancelTrip(val tripId: Int) : DestinationIntent
    data class StartPacking(val trip: UITrip) : DestinationIntent
}