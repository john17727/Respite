package dev.juanrincon.trips.presentation.empty_screen

sealed interface EmptyScreenEvent {
    data object TripCreationSuccess : EmptyScreenEvent
    data object PackForDestination : EmptyScreenEvent
    data object Destination : EmptyScreenEvent
    data object PackForNextDestination : EmptyScreenEvent
}