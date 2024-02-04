package presentation.trip

import domain.model.Trip
import domain.model.TripItem

data class TripState(
    val trip: Trip? = null,
    val items: List<TripItem> = listOf()
)
