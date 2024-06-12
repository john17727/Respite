package dev.juanrincon.trips.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.juanrincon.trips.presentation.destination.DestinationScreenRoot
import dev.juanrincon.trips.presentation.empty_screen.EmptyScreenRoot
import dev.juanrincon.trips.presentation.pack_destination.PackForDestinationScreenRoot
import org.koin.core.parameter.parametersOf

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
                onNavigateToPackForDestination = { tripId ->
                    navController.navigate(route = "pack_for_destination/$tripId") {
                        popUpTo("empty")
                    }
                },
                onNavigateToDestination = {
                    navController.navigate(route = "destination")
                },
                onNavigateToNextDestination = {
                    navController.navigate(route = "next_destination")
                }
            )
        }
        composable(
            route = "pack_for_destination/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) {
            PackForDestinationScreenRoot(
                onNavigateBack = {
                    navController.navigateUp()
                },
                parametersHolder = parametersOf(it.arguments?.getInt("tripId") ?: 0)
            )
        }
        composable(route = "destination") {
            DestinationScreenRoot()
        }
    }
}
