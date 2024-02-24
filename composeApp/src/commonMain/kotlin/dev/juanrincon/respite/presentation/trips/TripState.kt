package dev.juanrincon.respite.presentation.trips

import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.domain.model.TripItem

data class TripState(
    val trip: Trip? = null,
    val items: List<TripItem> = listOf(),
    val loading: Boolean = false
)
