package dev.juanrincon.trips.presentation.utils

import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.trips.domain.Trip
import dev.juanrincon.trips.domain.TripItem
import dev.juanrincon.trips.domain.extensions.getCityAbbreviation
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.models.UITripStatus

inline fun Trip.toUIModel() = UITrip(
    this.id,
    this.name.getCityAbbreviation(),
    this.status.toUIModel(),
    this.current,
    this.items.map { it.toUIModel() }
)

inline fun TripItem.toUIModel() = UITripItem(
    this.id,
    this.name,
    this.category,
    this.total
)

inline fun TripStatus.toUIModel() = when (this) {
    TripStatus.Destination -> UITripStatus.Destination
    TripStatus.PackingDestination -> UITripStatus.PackingDestination
    TripStatus.PackingNextDestination -> UITripStatus.PackingNextDestination
}

inline fun UITrip.toDomainModel() = Trip(
    this.id,
    this.name,
    this.status.toDomainModel(),
    this.active,
    this.items.map { it.toDomainModel() }
)

inline fun UITripStatus.toDomainModel() = when (this) {
    UITripStatus.Destination -> TripStatus.Destination
    UITripStatus.PackingDestination -> TripStatus.PackingDestination
    UITripStatus.PackingNextDestination -> TripStatus.PackingNextDestination
}

inline fun UITripItem.toDomainModel() = TripItem(
    this.id,
    this.name,
    this.category,
    this.total
)
