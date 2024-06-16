package dev.juanrincon.trips.presentation.destination

import dev.juanrincon.trips.presentation.models.UITrip

data class DestinationState(
    val trip: UITrip = UITrip.default,
    val loading: Boolean = false,
    val transitionAnimation: Boolean = false,
    val listAnimation: Boolean = false

)
