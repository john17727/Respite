package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.presentation.trip_list.TripListScreenModel
import dev.juanrincon.respite.presentation.trip_list.TripListUI

class TripListScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { TripListScreenModel() }
        val navigator = LocalNavigator.currentOrThrow
        val state by screenModel.state.collectAsState()
        TripListUI(
            state.trips,
            { id ->
                navigator.push(TripScreen(id))
            },
            {}
        )
    }

    companion object {
        val trips =
            listOf(
                Trip(1, "Las Vegas", 30),
                Trip(2, "Roswell", 20),
                Trip(3, "El Paso", 10),
            )
    }
}