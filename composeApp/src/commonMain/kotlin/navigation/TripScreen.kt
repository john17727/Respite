package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import domain.model.Trip
import presentation.trip.TripUI

data class TripScreen(private val id: Int) : Screen {

    @Composable
    override fun Content() {
        TripListScreen.trips.find { trip: Trip -> trip.id == id }?.let {
            TripUI(it)
        }
    }
}