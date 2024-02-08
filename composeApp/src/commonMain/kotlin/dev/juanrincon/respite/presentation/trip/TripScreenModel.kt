package dev.juanrincon.respite.presentation.trip

import cafe.adriel.voyager.core.model.ScreenModel
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.mvi
import dev.juanrincon.respite.navigation.TripListScreen

class TripScreenModel(private val tripId: Int) : ScreenModel, MVI<TripState, TripIntent, Nothing> by mvi(
    TripState()
) {

    init {
        TripListScreen.trips.find { trip -> trip.id == tripId }?.let {
            updateState { copy(trip = it) }
        }
    }

}