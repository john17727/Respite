package dev.juanrincon.trips.presentation.models

data class TripState(
    val trip: UITrip = UITrip.default,
    val loading: Boolean = false,
    val transitionAnimation: Boolean = false,
    val listAnimation: Boolean = false
)
