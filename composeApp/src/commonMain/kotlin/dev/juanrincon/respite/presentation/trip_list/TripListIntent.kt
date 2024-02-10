package presentation.trips

sealed interface TripListIntent {
    data object ReloadAllTrips: TripListIntent
}