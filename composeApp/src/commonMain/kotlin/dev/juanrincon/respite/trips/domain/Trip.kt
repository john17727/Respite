package dev.juanrincon.respite.trips.domain

import dev.juanrincon.core.domain.TripStatus

data class Trip(
    val id: Int,
    val name: String,
    val nameAbbr: String,
    val status: TripStatus,
    val current: Boolean,
    val items: List<TripItem>
)
