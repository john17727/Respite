package dev.juanrincon.respite.presentation.trips

sealed interface TripIntent {
    data class CreateTrip(val name: String) : TripIntent
}