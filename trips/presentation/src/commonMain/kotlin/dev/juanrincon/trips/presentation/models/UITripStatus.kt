package dev.juanrincon.trips.presentation.models

sealed interface UITripStatus {
    data object PackingDestination : UITripStatus
    data object Destination : UITripStatus
    data object PackingNextDestination : UITripStatus
}