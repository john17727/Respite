package dev.juanrincon.core.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DetailTripItem(
    @Embedded val tripItem: TripItem,
    @Relation(
        entity = Item::class,
        parentColumn = "item_id",
        entityColumn = "id"
    )
    val luggageItem: LuggageItem
)
