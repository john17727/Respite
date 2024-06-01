package dev.juanrincon.trips.presentation.models

data class TripState(
    val trip: UITrip? = null,
    val loading: Boolean = false
)
