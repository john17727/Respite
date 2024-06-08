package dev.juanrincon.trips.presentation.empty_screen

sealed interface EmptyScreenIntent {
    data object GetTrips : EmptyScreenIntent
    data class CreateTrip(val name: String) : EmptyScreenIntent
    data object NavigateToCategories : EmptyScreenIntent
    data object NavigateToLuggage : EmptyScreenIntent
    data object NavigateToPackForDestination : EmptyScreenIntent
}