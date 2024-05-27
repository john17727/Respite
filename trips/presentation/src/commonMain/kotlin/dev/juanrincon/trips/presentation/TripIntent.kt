package dev.juanrincon.trips.presentation

import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.trips.domain.Trip
import dev.juanrincon.trips.domain.TripItem

sealed interface TripIntent {
    data class CreateTrip(val name: String) : TripIntent
    data class AddItem(val tripId: Int, val item: TripItem) : TripIntent
    data class RemoveItem(val tripId: Int, val item: TripItem) : TripIntent
    data class CancelPacking(val tripId: Int, val tripStatus: TripStatus) : TripIntent
    data class FinishPacking(val trip: Trip) : TripIntent
}