package dev.juanrincon.respite.trips.presentation

import dev.juanrincon.respite.trips.domain.Trip

data class TripState(
    val trip: Trip? = null,
    val loading: Boolean = false
)
