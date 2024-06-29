package dev.juanrincon.trips.data.utils

import dev.juanrincon.core.data.database.entities.DetailTripItem
import dev.juanrincon.core.data.database.entities.PotentialTripItem
import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.trips.domain.Trip
import dev.juanrincon.trips.domain.TripItem
import dev.juanrincon.core.data.database.entities.Trip as TripEntity
import dev.juanrincon.core.data.models.TripStatus as TripStatusEntity

fun TripStatusEntity.toDomain(): TripStatus = when (this) {
    TripStatusEntity.PackingDestination -> TripStatus.PackingDestination
    TripStatusEntity.Destination -> TripStatus.Destination
    TripStatusEntity.PackingNextDestination -> TripStatus.PackingNextDestination
}

fun TripStatus.toEntity(): TripStatusEntity = when (this) {
    TripStatus.Destination -> TripStatusEntity.Destination
    TripStatus.PackingDestination -> TripStatusEntity.PackingDestination
    TripStatus.PackingNextDestination -> TripStatusEntity.PackingNextDestination
}

fun TripEntity.toDomain(): Trip = Trip(
    id = this.id,
    name = this.name,
    status = this.status.toDomain(),
    current = this.current,
    items = listOf()
)

fun TripEntity.toDomain(items: List<TripItem>): Trip = Trip(
    id = this.id,
    name = this.name,
    status = this.status.toDomain(),
    current = this.current,
    items = items
)


fun DetailTripItem.toDomain(): TripItem = TripItem(
    id = this.luggageItem.item.id,
    name = this.luggageItem.item.name,
    category = this.luggageItem.category.name,
    total = this.tripItem.amount,
    packed = this.tripItem.packed
)

fun PotentialTripItem.toDomain(): TripItem = TripItem(
    id = this.id,
    name = this.name,
    category = this.category,
    total = this.amount,
    packed = 0
)

fun Trip.toEntity(): TripEntity = TripEntity(
    id = this.id,
    name = this.name,
    status = this.status.toEntity(),
    current = this.current
)
