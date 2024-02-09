package dev.juanrincon.respite.presentation.categories

import dev.juanrincon.respite.domain.model.Category

sealed class CategoryItem(val id: Int) {
    data class SystemItem(val category: Category) : CategoryItem(category.id)
    data class UserItem(val category: Category) : CategoryItem(category.id)

    data class EditingItem(val category: Category) : CategoryItem(category.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.category)
    }
}