package presentation.trip_list

import cafe.adriel.voyager.core.model.ScreenModel
import mvi.MVI
import mvi.mvi
import navigation.TripListScreen
import presentation.trips.TripListIntent
import presentation.trips.TripListState

class TripListScreenModel : ScreenModel, MVI<TripListState, TripListIntent, Nothing> by mvi(TripListState()) {

    init {
        updateState { copy(trips = TripListScreen.trips) }
    }
}