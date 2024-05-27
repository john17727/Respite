package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.trips.presentation.TripIntent
import dev.juanrincon.trips.presentation.TripScreenModel
import dev.juanrincon.trips.presentation.TripsUI

class TripScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<TripScreenModel>()
        val state by screenModel.state.collectAsState()
        TripsUI(
            state = state,
            onCreateNewTrip = {
                screenModel.onIntent(TripIntent.CreateTrip(it))
            },
            onCategoriesClick = {
                navigator.push(CategoriesScreen())
            },
            onLuggageClick = {
                navigator.push(LuggageScreen())
            },
            onAddItemCountClick = { tripId, item ->
                screenModel.onIntent(TripIntent.AddItem(tripId, item))
            },
            onRemoveItemCountClick = { tripId, item ->
                screenModel.onIntent(TripIntent.RemoveItem(tripId, item))
            },
            onCancelPackingClick = { tripId, status ->
                screenModel.onIntent(TripIntent.CancelPacking(tripId, status))
            },
            onFinishPackingClick = { trip ->
                screenModel.onIntent(TripIntent.FinishPacking(trip))
            }
        )
    }
}