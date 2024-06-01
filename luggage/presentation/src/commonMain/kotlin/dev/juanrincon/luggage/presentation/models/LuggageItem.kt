package dev.juanrincon.luggage.presentation

import dev.juanrincon.luggage.presentation.models.UICategory
import dev.juanrincon.luggage.presentation.models.UIItem


sealed class LuggageItem(val id: Int) {
    data class UserItem(val item: UIItem) : LuggageItem(item.id)
    data class EditingItem(val item: UIItem) : LuggageItem(item.id)
    data class CreatingItem(val item: UIItem) : LuggageItem(item.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.item)
        fun EditingItem.toUserItem(): UserItem = UserItem(this.item)
        fun newItem() = CreatingItem(UIItem(0, "", UICategory(0, "")))
    }
}