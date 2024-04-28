package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.respite.luggage.presentation.LuggageIntent
import dev.juanrincon.respite.luggage.presentation.LuggageScreenModel
import dev.juanrincon.respite.luggage.presentation.LuggageUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LuggageScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        var showContent by remember { mutableStateOf(false) }
        LifecycleEffect(
            onStarted = {
                showContent = true
            }
        )
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<LuggageScreenModel>()
        val state by screenModel.state.collectAsState()
        LuggageUI(
            luggage = state.luggage,
            categories = state.categories,
            inEditMode = state.inEditMode,
            inAddMode = state.inAddMode,
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
                scope.launch {
                    showContent = showContent.not()
                    delay(200)
                    navigator.pop()
                }
            },
            showContent = showContent
        )
    }
}