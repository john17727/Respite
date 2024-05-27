package dev.juanrincon.trips.presentation

import dev.juanrincon.trips.domain.Trip

data class TripState(
    val trip: Trip? = null,
    val loading: Boolean = false
)
