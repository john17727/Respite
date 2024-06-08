package dev.juanrincon.trips.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.juanrincon.trips.presentation.empty_screen.EmptyScreenRoot
import dev.juanrincon.trips.presentation.pack_destination.PackForDestinationScreenRoot

fun NavGraphBuilder.tripsGraph(navController: NavHostController) {
    navigation(
        startDestination = "empty",
        route = "trips"
    ) {
        composable(route = "empty") {
            EmptyScreenRoot(
                onCategoriesClick = {
                    navController.navigate(route = "categories")
                },
                onLuggageClick = {
                    navController.navigate(route = "luggage")
                },
                onNavigateToPackForDestination = {
                    navController.navigate(route = "pack_for_destination")
                },
                onNavigateToDestination = {
                    navController.navigate(route = "destination")
                },
                onNavigateToNextDestination = {
                    navController.navigate(route = "next_destination")
                }
            )
        }
        composable(route = "pack_for_destination") {
            PackForDestinationScreenRoot(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
