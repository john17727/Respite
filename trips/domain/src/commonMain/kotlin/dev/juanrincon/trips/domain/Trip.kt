package dev.juanrincon.trips.domain

import dev.juanrincon.core.domain.TripStatus

data class Trip(
    val id: Int,
    val name: String,
    val status: TripStatus,
    val current: Boolean,
    val items: List<TripItem>
)
