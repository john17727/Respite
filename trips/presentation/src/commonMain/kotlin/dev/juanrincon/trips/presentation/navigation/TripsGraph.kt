package dev.juanrincon.trips.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.juanrincon.trips.presentation.empty_screen.EmptyScreenRoot

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
                }
            )
        }
    }
}
