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
        is CategoryIntent.EditItem -> setCategoryEditable(intent.id)
        is CategoryIntent.UpdateItem -> updateCategoryProperty(
            intent.id,
            intent.name
        )

        CategoryIntent.CreateItem -> addCreateItem()
        is CategoryIntent.CreateCategory -> createCategory(intent.newCategory)
    }

    private fun createCategory(newCategory: Category) {
        screenModelScope.launch {
            repository.create(newCategory).fold(
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

    private fun updateCategoryProperty(id: Int, name: String) {
        updateState { copy(categories = setUpdatedProperty(this.categories, id, name)) }
    }

    private fun setCategoryEditable(id: Int) {
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

    private fun updateCategory(category: Category) {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.update(category).fold(
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
            mutableList[indexOfEditable] =
                CategoryItem.EditingItem((item as CategoryItem.UserItem).category)
            mutableList
        } ?: categories

    private fun setUpdatedProperty(
        categories: List<CategoryItem>,
        id: Int,
        name: String
    ): List<CategoryItem> = categories.find { it.id == id }?.let { item ->
        val mutableList = categories.toMutableList()
        val indexOfEditable = categories.indexOf(item)
        mutableList[indexOfEditable] = when (mutableList[indexOfEditable]) {
            is CategoryItem.EditingItem -> CategoryItem.EditingItem(Category(id, name, null))
            else -> CategoryItem.CreatingItem(Category(id, name, null))
        }
        mutableList
    } ?: categories

    private fun addItemEditableInList(categories: List<CategoryItem>): List<CategoryItem> {
        val mutableList = categories.toMutableList()
        mutableList.add(CategoryItem.CreatingItem(Category(0, "", null)))
        return mutableList
    }
}