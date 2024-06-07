package dev.juanrincon.trips.presentation.pack_destination

import dev.juanrincon.trips.presentation.models.UITrip

data class PackForDestinationState(
    val trip: UITrip,
    val loading: Boolean = false
)
