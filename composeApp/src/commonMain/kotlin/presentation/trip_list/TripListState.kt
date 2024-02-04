package presentation.trips

import domain.model.Trip

data class TripListState(
    val loading: Boolean = false,
    val trips: List<Trip> = listOf(),
    val errorMessage: String = ""
)
