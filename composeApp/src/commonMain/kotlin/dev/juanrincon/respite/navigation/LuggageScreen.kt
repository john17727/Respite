package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import dev.juanrincon.respite.presentation.luggage.LuggageScreenModel
import dev.juanrincon.respite.presentation.luggage.LuggageUI

class LuggageScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LuggageScreenModel>()
        val state by screenModel.state.collectAsState()
        LuggageUI(
            state.luggage
        )
    }
}