package dev.juanrincon.respite.presentation.trip_list

import cafe.adriel.voyager.core.model.ScreenModel
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.mvi
import dev.juanrincon.respite.navigation.TripListScreen
import presentation.trips.TripListIntent
import presentation.trips.TripListState

class TripListScreenModel : ScreenModel, MVI<TripListState, TripListIntent, Nothing> by mvi(TripListState()) {

    init {
        updateState { copy(trips = TripListScreen.trips) }
    }
}