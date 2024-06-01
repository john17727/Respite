package dev.juanrincon.trips.presentation.models

sealed interface TripIntent {
    data class CreateTrip(val name: String) : TripIntent
    data class AddItem(val tripId: Int, val item: UITripItem) : TripIntent
    data class RemoveItem(val tripId: Int, val item: UITripItem) : TripIntent
    data class CancelPacking(val tripId: Int, val tripStatus: UITripStatus) : TripIntent
    data class FinishPacking(val trip: UITrip) : TripIntent
}