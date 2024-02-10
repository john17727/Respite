package dev.juanrincon.respite.presentation.categories

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.domain.repository.CategoryRepository
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.MVIDelegate
import dev.juanrincon.respite.presentation.categories.CategoryItem.Companion.toEditingItem
import dev.juanrincon.respite.presentation.categories.CategoryItem.Companion.toUserItem
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
        is CategoryIntent.UpdateCategory -> updateCategory(intent.id, intent.newName)
        is CategoryIntent.EditItem -> setEditItem(intent.id)
        CategoryIntent.CreateItem -> addCreateItem()
        is CategoryIntent.CreateCategory -> createCategory(intent.name)
        CategoryIntent.CancelCreateItem -> cancelCreateItem()
        is CategoryIntent.CancelEditItem -> cancelEditItem(intent.id)
    }

    private fun cancelEditItem(id: Int) {
        updateState {
            copy(
                categories = setEditableItemToUserItem(categories, id),
                inEditMode = false
            )
        }
    }

    private fun setEditableItemToUserItem(
        categories: List<CategoryItem>,
        id: Int
    ): List<CategoryItem> =
        categories.find { it.id == id }?.let { item ->
            val mutableList = categories.toMutableList()
            val indexOfEditable = categories.indexOf(item)
            mutableList[indexOfEditable] = (item as CategoryItem.EditingItem).toUserItem()
            mutableList
        } ?: categories


    private fun cancelCreateItem() {
        updateState { copy(categories = removeEditableInList(this.categories), inEditMode = false) }
    }

    private fun removeEditableInList(categories: List<CategoryItem>): List<CategoryItem> =
        categories.find { it.id == 0 }?.let { item ->
            val mutableList = categories.toMutableList()
            mutableList.remove(item)
            mutableList
        } ?: categories

    private fun createCategory(name: String) {
        screenModelScope.launch {
            repository.create(Category(0, name, null)).fold(
                onSuccess = {
                    getCategories()
                },
                onFailure = {
                    updateState { copy(loading = false, inEditMode = false) }
                }
            )
        }
    }

    private fun addCreateItem() {
        updateState { copy(categories = addItemEditableInList(this.categories), inEditMode = true) }
    }

    private fun setEditItem(id: Int) {
        updateState {
            copy(
                categories = setItemEditableInList(this.categories, id),
                inEditMode = true
            )
        }
    }

    private fun getCategories() {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.read().fold(
                onSuccess = {
                    updateState {
                        copy(
                            categories = it.map(::toCategoryItem),
                            loading = false,
                            inEditMode = false
                        )
                    }
                },
                onFailure = {
                    updateState { copy(loading = false, inEditMode = false) }
                }
            )
        }
    }

    private fun updateCategory(id: Int, name: String) {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.update(Category(id, name, null)).fold(
                onSuccess = {
                    getCategories()
                },
                onFailure = {
                    updateState { copy(loading = false, inEditMode = false) }
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
            val mutableList = categories.toMutableList()
            val indexOfEditable = categories.indexOf(item)
            mutableList[indexOfEditable] = (item as CategoryItem.UserItem).toEditingItem()
            mutableList
        } ?: categories

    private fun addItemEditableInList(categories: List<CategoryItem>): List<CategoryItem> {
        val mutableList = categories.toMutableList()
        mutableList.add(CategoryItem.CreatingItem(Category(0, "", null)))
        return mutableList
    }
}