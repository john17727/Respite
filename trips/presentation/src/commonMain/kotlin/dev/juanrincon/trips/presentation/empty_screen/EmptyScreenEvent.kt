package dev.juanrincon.trips.presentation.empty_screen

sealed interface EmptyScreenEvent {
    data class TripCreationSuccess(val tripId: Int) : EmptyScreenEvent
    data class PackForDestination(val tripId: Int) : EmptyScreenEvent
    data object Destination : EmptyScreenEvent
    data object PackForNextDestination : EmptyScreenEvent
}