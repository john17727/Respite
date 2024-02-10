package dev.juanrincon.respite.presentation.categories

import dev.juanrincon.respite.domain.model.Category

sealed class CategoryItem(val id: Int) {
    data class SystemItem(val category: Category) : CategoryItem(category.id)
    data class UserItem(val category: Category) : CategoryItem(category.id)

    data class EditingItem(val category: Category, val originalName: String) :
        CategoryItem(category.id)

    data class CreatingItem(val category: Category) : CategoryItem(category.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.category, this.category.name)
        fun EditingItem.toUserItem(): UserItem =
            UserItem(Category(this.id, this.originalName, this.category.description))
    }
}