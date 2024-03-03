package dev.juanrincon.respite.presentation.trips

sealed interface TripIntent {
    data object StartCreateTrip : TripIntent
    data class CreateTrip(val name: String) : TripIntent
}