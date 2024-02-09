package dev.juanrincon.respite.presentation.categories

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.domain.repository.CategoryRepository
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.MVIDelegate
import kotlinx.coroutines.launch

class CategoriesScreenModel(private val repository: CategoryRepository) : ScreenModel,
    MVI<CategoryState, CategoryIntent, Nothing> by MVIDelegate(
        CategoryState()
    ) {

    init {
        getCategories()
    }

    override fun onIntent(intent: CategoryIntent) = when (intent) {
        CategoryIntent.GetAllCategories -> getCategories()
        is CategoryIntent.DeleteCategory -> deleteCategory(intent.id)
        is CategoryIntent.UpdateCategory -> updateCategory(intent.newCategory)
    }

    private fun getCategories() {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.read().fold(
                onSuccess = {
                    updateState { copy(categories = it.map(::toCategoryItem), loading = false) }
                },
                onFailure = {
                    updateState { copy(loading = false) }
                }
            )
        }
    }

    private fun updateCategory(category: Category) {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.update(category).fold(
                onSuccess = {
                    getCategories()
                },
                onFailure = {
                    updateState { copy(loading = false) }
                }
            )
        }
    }

    private fun deleteCategory(id: Int) {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.delete(id).fold(
                onSuccess = {
                    getCategories()
                },
                onFailure = {
                    updateState { copy(loading = false) }
                }
            )
        }
    }

    private fun toCategoryItem(category: Category): CategoryItem =
        if (category.description !== null) {
            CategoryItem.SystemItem(category)
        } else {
            CategoryItem.UserItem(category)
        }
}