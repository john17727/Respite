package dev.juanrincon.trips.presentation.empty_screen

import dev.juanrincon.trips.presentation.models.UITrip

data class EmptyScreenState(
    val trip: UITrip? = null,
    val loading: Boolean = false,
)
