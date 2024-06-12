package dev.juanrincon.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "trip_item", primaryKeys = ["trip_id", "item_id"])
data class TripItem(
    @ColumnInfo(name = "trip_id") val tripId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    val amount: Int
)
