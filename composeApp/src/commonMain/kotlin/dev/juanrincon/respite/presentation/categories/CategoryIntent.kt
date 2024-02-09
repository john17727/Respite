package dev.juanrincon.respite.presentation.categories

import dev.juanrincon.respite.domain.model.Category

sealed interface CategoryIntent {
    data object GetAllCategories : CategoryIntent

    data class UpdateCategory(val newCategory: Category) : CategoryIntent

    data class DeleteCategory(val id: Int) : CategoryIntent

    data class EditCategory(val id: Int) : CategoryIntent

    data class OnUpdateCategoryProperties(val id: Int, val name: String) : CategoryIntent
}