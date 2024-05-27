package dev.juanrincon.categories.presentation

import dev.juanrincon.core.domain.Category


sealed class CategoryItem(val id: Int) {
    data class SystemItem(val category: Category) : CategoryItem(category.id)
    data class UserItem(val category: Category) : CategoryItem(category.id)

    data class EditingItem(val category: Category) : CategoryItem(category.id)

    data class CreatingItem(val category: Category) : CategoryItem(category.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.category)
        fun EditingItem.toUserItem(): UserItem =
            // TODO: Change this to just pass the item
            UserItem(Category(this.id, this.category.name, this.category.description))
    }
}