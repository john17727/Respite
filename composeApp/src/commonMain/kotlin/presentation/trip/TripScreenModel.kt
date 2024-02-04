package presentation.trip

import cafe.adriel.voyager.core.model.ScreenModel
import mvi.MVI
import mvi.mvi
import navigation.TripListScreen

class TripScreenModel(private val tripId: Int) : ScreenModel, MVI<TripState, TripIntent, Nothing> by mvi(TripState()) {

    init {
        TripListScreen.trips.find { trip -> trip.id == tripId }?.let {
            updateState { copy(trip = it) }
        }
    }

}