package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Trip
import presentation.trip.TripScreenModel
import presentation.trip.TripUI

data class TripScreen(private val id: Int) : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { TripScreenModel(id) }
        val navigator = LocalNavigator.currentOrThrow
        val state by screenModel.state.collectAsState()
        state.trip?.let {
            TripUI(it)
        }
    }
}