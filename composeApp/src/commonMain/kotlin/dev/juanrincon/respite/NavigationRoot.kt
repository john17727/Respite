package dev.juanrincon.respite

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.juanrincon.categories.presentation.CategoriesScreenRoot
import dev.juanrincon.luggage.presentation.LuggageScreenRoot
import dev.juanrincon.trips.presentation.navigation.tripsGraph

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "trips"
    ) {
        tripsGraph(navController)
        composable(route = "categories") {
            CategoriesScreenRoot(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = "luggage") {
            LuggageScreenRoot(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}