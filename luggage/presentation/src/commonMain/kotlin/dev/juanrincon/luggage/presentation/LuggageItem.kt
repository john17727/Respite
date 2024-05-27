package dev.juanrincon.luggage.presentation

import dev.juanrincon.luggage.domain.Item


sealed class LuggageItem(val id: Int) {
    data class UserItem(val item: Item) : LuggageItem(item.id)
    data class EditingItem(val item: Item) : LuggageItem(item.id)
    data class CreatingItem(val item: Item) : LuggageItem(item.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.item)
        fun EditingItem.toUserItem(): UserItem = UserItem(this.item)
    }
}