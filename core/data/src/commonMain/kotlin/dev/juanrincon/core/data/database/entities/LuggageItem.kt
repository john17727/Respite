package dev.juanrincon.core.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class LuggageItem(
    @Embedded val item: Item,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category
)
