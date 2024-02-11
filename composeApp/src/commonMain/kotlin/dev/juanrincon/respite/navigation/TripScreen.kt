package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.respite.presentation.trips.TripsUI

class TripScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TripsUI(
            onCategoriesClick = {
                navigator.push(CategoriesScreen())
            },
            onLuggageClick = {
                navigator.push(LuggageScreen())
            }
        )
    }
}