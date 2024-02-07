package presentation.categories

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.repository.CategoryRepository
import kotlinx.coroutines.launch
import mvi.MVI
import mvi.MVIDelegate

class CategoriesScreenModel(private val repository: CategoryRepository) : ScreenModel, MVI<CategoryState, CategoryIntent, Nothing> by MVIDelegate(
    CategoryState()
) {

    init {
        getCategories()
    }

    override fun onIntent(intent: CategoryIntent) = when (intent) {
        CategoryIntent.GetAllCategories -> getCategories()
    }

    private fun getCategories() {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.read().fold(
                onSuccess = {
                    updateState { copy(categories = it, loading = false) }
                },
                onFailure = {
                    updateState { copy(loading = false) }
                }
            )
        }
    }
}