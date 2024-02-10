package dev.juanrincon.respite.presentation.categories

import dev.juanrincon.respite.domain.model.Category

sealed interface CategoryIntent {
    data object GetAllCategories : CategoryIntent

    data class UpdateCategory(val newCategory: Category) : CategoryIntent

    data class DeleteCategory(val id: Int) : CategoryIntent

    data class CreateCategory(val newCategory: Category) : CategoryIntent

    data class EditItem(val id: Int) : CategoryIntent

    data class UpdateItem(val id: Int, val name: String) : CategoryIntent
    data class CancelEditItem(val id: Int) : CategoryIntent

    data object CreateItem : CategoryIntent

    data object CancelCreateItem : CategoryIntent
}