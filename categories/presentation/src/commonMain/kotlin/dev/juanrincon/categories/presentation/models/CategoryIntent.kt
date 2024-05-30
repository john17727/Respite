package dev.juanrincon.categories.presentation.models

sealed interface CategoryIntent {
    data object GetAllCategories : CategoryIntent

    data class UpdateCategory(val id: Int, val newName: String) : CategoryIntent

    data class DeleteCategory(val id: Int) : CategoryIntent

    data class CreateCategory(val name: String) : CategoryIntent

    data class EditItem(val id: Int) : CategoryIntent

    data class CancelEditItem(val id: Int) : CategoryIntent

    data object CreateItem : CategoryIntent

    data object CancelCreateItem : CategoryIntent
}