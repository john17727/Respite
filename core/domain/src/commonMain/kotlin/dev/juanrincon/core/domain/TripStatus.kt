package dev.juanrincon.core.domain

sealed interface TripStatus {
    data object PackingDestination : TripStatus
    data object Destination : TripStatus
    data object PackingNextDestination : TripStatus
}
