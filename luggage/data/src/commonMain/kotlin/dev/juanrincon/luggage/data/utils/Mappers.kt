package dev.juanrincon.luggage.data.utils

import dev.juanrincon.core.data.database.entities.LuggageItem
import dev.juanrincon.core.domain.Category
import dev.juanrincon.luggage.domain.Item

fun LuggageItem.toDomain() = Item(
    id = this.item.id,
    name = this.item.name,
    category = Category(
        id = this.category.id,
        name = this.category.name,
        description = this.category.description
    )
)