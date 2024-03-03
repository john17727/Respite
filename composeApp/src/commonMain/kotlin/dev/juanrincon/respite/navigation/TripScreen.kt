package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.respite.presentation.trips.TripIntent
import dev.juanrincon.respite.presentation.trips.TripScreenModel
import dev.juanrincon.respite.presentation.trips.TripsUI

class TripScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<TripScreenModel>()
        val state by screenModel.state.collectAsState()
        TripsUI(
            state = state,
            onToggleCreateNewTrip = {
                screenModel.onIntent(TripIntent.StartCreateTrip)
            },
            onCreateNewTrip = {
                screenModel.onIntent(TripIntent.CreateTrip(it))
            },
            onCategoriesClick = {
                navigator.push(CategoriesScreen())
            },
            onLuggageClick = {
                navigator.push(LuggageScreen())
            }
        )
    }
}