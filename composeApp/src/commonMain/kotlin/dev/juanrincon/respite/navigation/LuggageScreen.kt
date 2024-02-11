package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.respite.presentation.luggage.LuggageIntent
import dev.juanrincon.respite.presentation.luggage.LuggageScreenModel
import dev.juanrincon.respite.presentation.luggage.LuggageUI

class LuggageScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<LuggageScreenModel>()
        val state by screenModel.state.collectAsState()
        LuggageUI(
            luggage = state.luggage,
            categories = state.categories,
            inEditMode = state.inEditMode,
            onDeleteClick = { id -> screenModel.onIntent(LuggageIntent.DeleteLuggage(id)) },
            onEditClick = { id -> screenModel.onIntent(LuggageIntent.EditItem(id)) },
            onEditSave = { id, name, categoryId ->
                screenModel.onIntent(
                    LuggageIntent.UpdateLuggage(
                        id,
                        name,
                        categoryId
                    )
                )
            },
            onEditCancel = { id -> screenModel.onIntent(LuggageIntent.CancelEditItem(id)) },
            onCreateClick = { screenModel.onIntent(LuggageIntent.CreateItem) },
            onCreateSave = { name, categoryId ->
                screenModel.onIntent(
                    LuggageIntent.CreateLuggage(
                        name,
                        categoryId
                    )
                )
            },
            onCreateCancel = { screenModel.onIntent(LuggageIntent.CancelCreateItem) },
            onBackClick = {
                navigator.pop()
            }
        )
    }
}