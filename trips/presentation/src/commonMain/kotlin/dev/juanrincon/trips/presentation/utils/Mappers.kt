package dev.juanrincon.trips.presentation.utils

import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.trips.domain.Trip
import dev.juanrincon.trips.domain.TripItem
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.models.UITripStatus

fun Trip.toUIModel() = UITrip(
    this.id,
    this.name,
    this.status.toUIModel(),
    this.current,
    this.items.map { it.toUIModel() }
)

fun TripItem.toUIModel() = UITripItem(
    this.id,
    this.name,
    this.category,
    this.total
)

fun TripStatus.toUIModel() = when (this) {
    TripStatus.Destination -> UITripStatus.Destination
    TripStatus.PackingDestination -> UITripStatus.PackingDestination
    TripStatus.PackingNextDestination -> UITripStatus.PackingNextDestination
}

fun UITrip.toDomainModel() = Trip(
    this.id,
    this.name,
    this.status.toDomainModel(),
    this.active,
    this.items.map { it.toDomainModel() }
)

fun UITripStatus.toDomainModel() = when (this) {
    UITripStatus.Destination -> TripStatus.Destination
    UITripStatus.PackingDestination -> TripStatus.PackingDestination
    UITripStatus.PackingNextDestination -> TripStatus.PackingNextDestination
}

fun UITripItem.toDomainModel() = TripItem(
    this.id,
    this.name,
    this.category,
    this.total
)
