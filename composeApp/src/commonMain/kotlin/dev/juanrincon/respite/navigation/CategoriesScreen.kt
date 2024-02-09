package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import dev.juanrincon.respite.presentation.categories.CategoriesScreenModel
import dev.juanrincon.respite.presentation.categories.CategoriesUI
import dev.juanrincon.respite.presentation.categories.CategoryIntent

class CategoriesScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<CategoriesScreenModel>()
        val state by screenModel.state.collectAsState()
        CategoriesUI(
            state.categories,
            state.inEditMode,
            { id -> screenModel.onIntent(CategoryIntent.EditItem(id)) },
            { id -> screenModel.onIntent(CategoryIntent.DeleteCategory(id)) },
            { id, value ->
                screenModel.onIntent(
                    CategoryIntent.UpdateItem(
                        id,
                        value
                    )
                )
            },
            { updatedCategory -> screenModel.onIntent(CategoryIntent.UpdateCategory(updatedCategory)) },
            { screenModel.onIntent(CategoryIntent.CreateItem) },
            { newCategory -> screenModel.onIntent(CategoryIntent.CreateCategory(newCategory)) }
        )
    }
}