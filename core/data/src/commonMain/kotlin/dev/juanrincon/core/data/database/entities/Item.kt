package dev.juanrincon.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int
)
