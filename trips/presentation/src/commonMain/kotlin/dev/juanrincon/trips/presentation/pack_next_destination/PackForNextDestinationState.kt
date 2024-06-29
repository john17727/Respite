package dev.juanrincon.trips.presentation.pack_next_destination

import dev.juanrincon.trips.presentation.models.UITrip

data class PackForNextDestinationState(
    val trip: UITrip = UITrip.default,
    val loading: Boolean = false,
    val isNextButtonEnabled: Boolean = false,
    val transitionAnimation: Boolean = false,
    val listAnimation: Boolean = false
)
