package dev.juanrincon.respite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.juanrincon.trips.presentation.navigation.tripsGraph

@Composable
fun NavigationRoot(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "trips"
    ) {
        tripsGraph(navController)
    }
}