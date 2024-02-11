package dev.juanrincon.respite.presentation.luggage

import dev.juanrincon.respite.domain.model.Item

sealed class LuggageItem(val id: Int) {
    data class UserItem(val item: Item) : LuggageItem(item.id)
    data class EditingItem(val item: Item) : LuggageItem(item.id)
    data class CreatingItem(val item: Item) : LuggageItem(item.id)

    companion object {
        fun UserItem.toEditingItem(): EditingItem = EditingItem(this.item)
    }
}