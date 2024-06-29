package dev.juanrincon.trips.presentation.pack_next_destination

import dev.juanrincon.trips.presentation.models.UITripItem

sealed interface PackForNextDestinationIntent {
    data class PackItem(val tripId: Int, val item: UITripItem) : PackForNextDestinationIntent
    data class UnpackItem(val tripId: Int, val item: UITripItem) : PackForNextDestinationIntent
    data class CancelPacking(val tripId: Int) : PackForNextDestinationIntent
    data class FinishPacking(val tripId: Int) : PackForNextDestinationIntent
}