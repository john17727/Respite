package dev.juanrincon.categories.presentation.models


sealed class CategoryItem(val id: Int) {
    data class SystemItem(val category: UICategory) : CategoryItem(category.id)
    data class UserItem(val category: UICategory) : CategoryItem(category.id)

    data class EditingItem(val category: UICategory) : CategoryItem(category.id)

    data class CreatingItem(val category: UICategory) : CategoryItem(category.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.category)
        fun EditingItem.toUserItem(): UserItem =
            UserItem(UICategory(this.id, this.category.name, this.category.description))
        fun newItem() = CreatingItem(UICategory(0, "", null))
    }
}