package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.juanrincon.respite.presentation.categories.CategoriesScreenModel
import dev.juanrincon.respite.presentation.categories.CategoriesUI
import dev.juanrincon.respite.presentation.categories.CategoryIntent

class CategoriesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<CategoriesScreenModel>()
        val state by screenModel.state.collectAsState()
        CategoriesUI(
            categories = state.categories,
            inEditMode = state.inEditMode,
            onEditClick = { id -> screenModel.onIntent(CategoryIntent.EditItem(id)) },
            onDeleteClick = { id -> screenModel.onIntent(CategoryIntent.DeleteCategory(id)) },
            onEditSave = { id, newName ->
                screenModel.onIntent(
                    CategoryIntent.UpdateCategory(
                        id,
                        newName
                    )
                )
            },
            onCreateClick = { screenModel.onIntent(CategoryIntent.CreateItem) },
            onCreateSave = { categoryName ->
                screenModel.onIntent(
                    CategoryIntent.CreateCategory(
                        categoryName
                    )
                )
            },
            onCreateCancel = { screenModel.onIntent(CategoryIntent.CancelCreateItem) },
            onEditCancel = { id -> screenModel.onIntent(CategoryIntent.CancelEditItem(id)) },
            onBackClick = {
                navigator.pop()
            }
        )
    }
}