package dev.juanrincon.trips.presentation.pack_destination

import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.models.UITripStatus

sealed interface PackForDestinationIntent {
    data class AddItem(val tripId: Int, val item: UITripItem) : PackForDestinationIntent
    data class RemoveItem(val tripId: Int, val item: UITripItem) : PackForDestinationIntent
    data class CancelPacking(val tripId: Int, val tripStatus: UITripStatus) :
        PackForDestinationIntent

    data class FinishPacking(val trip: UITrip) : PackForDestinationIntent
}