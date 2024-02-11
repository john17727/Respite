package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.juanrincon.respite.presentation.trips.TripsUI

class TripScreen : Screen {

    @Composable
    override fun Content() {
        TripsUI()
    }
}