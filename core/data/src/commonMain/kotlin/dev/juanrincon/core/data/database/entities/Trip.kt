package dev.juanrincon.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.juanrincon.core.data.models.TripStatus

@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val status: TripStatus,
    val current: Boolean
)
