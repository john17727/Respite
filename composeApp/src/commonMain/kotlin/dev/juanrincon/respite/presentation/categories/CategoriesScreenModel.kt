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
        is CategoryIntent.EditCategory -> setCategoryEditable(intent.id)
        is CategoryIntent.OnUpdateCategoryProperties -> updateCategoryProperty(
            intent.id,
            intent.name
        )
    }

    private fun updateCategoryProperty(id: Int, name: String) {
        updateState { copy(categories = setUpdatedProperty(this.categories, id, name)) }
    }

    private fun setCategoryEditable(id: Int) {
        updateState { copy(categories = setItemEditableInList(this.categories, id)) }
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

    private fun setItemEditableInList(categories: List<CategoryItem>, id: Int): List<CategoryItem> =
        categories.find { it.id == id }?.let { item ->
            val indexOfEditable = categories.indexOf(item)
            val mutableList = categories.toMutableList()
            mutableList[indexOfEditable] =
                CategoryItem.EditingItem((item as CategoryItem.UserItem).category)
            mutableList
        } ?: categories

    private fun setUpdatedProperty(
        categories: List<CategoryItem>,
        id: Int,
        name: String
    ): List<CategoryItem> = categories.find { it.id == id }?.let { item ->
        val indexOfEditable = categories.indexOf(item)
        val mutableList = categories.toMutableList()
        mutableList[indexOfEditable] = CategoryItem.EditingItem(Category(id, name, null))
        mutableList
    } ?: categories
}