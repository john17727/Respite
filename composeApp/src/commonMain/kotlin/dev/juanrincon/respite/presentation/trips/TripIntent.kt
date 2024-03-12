package dev.juanrincon.respite.presentation.trips

import dev.juanrincon.respite.domain.model.TripItem

sealed interface TripIntent {
    data class CreateTrip(val name: String) : TripIntent
    data class AddItem(val tripId: Int, val item: TripItem) : TripIntent
    data class RemoveItem(val tripId: Int, val item: TripItem) : TripIntent
}