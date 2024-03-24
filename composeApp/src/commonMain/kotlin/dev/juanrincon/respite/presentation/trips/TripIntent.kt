package dev.juanrincon.respite.presentation.trips

import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.domain.model.TripItem
import dev.juanrincon.respite.domain.model.TripStatus

sealed interface TripIntent {
    data class CreateTrip(val name: String) : TripIntent
    data class AddItem(val tripId: Int, val item: TripItem) : TripIntent
    data class RemoveItem(val tripId: Int, val item: TripItem) : TripIntent
    data class CancelPacking(val tripId: Int, val tripStatus: TripStatus) : TripIntent
    data class FinishPacking(val trip: Trip) : TripIntent
}