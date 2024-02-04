package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Trip
import presentation.trip_list.TripListUI

class TripListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TripListUI(
            trips,
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