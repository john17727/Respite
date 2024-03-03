package dev.juanrincon.respite.presentation.trips

import dev.juanrincon.respite.domain.model.Trip

data class TripState(
    val trip: Trip? = null,
    val createNewTrip: Boolean = false,
    val loading: Boolean = false
)
