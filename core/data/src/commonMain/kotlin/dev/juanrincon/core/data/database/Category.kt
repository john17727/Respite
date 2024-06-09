package dev.juanrincon.core.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String?
)
