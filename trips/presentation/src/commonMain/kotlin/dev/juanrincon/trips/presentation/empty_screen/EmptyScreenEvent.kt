package dev.juanrincon.trips.presentation.empty_screen

sealed interface EmptyScreenEvent {
    data class PackForDestination(val tripId: Int) : EmptyScreenEvent
    data class Destination(val tripId: Int) : EmptyScreenEvent
    data class PackForNextDestination(val tripId: Int) : EmptyScreenEvent
}