package dev.juanrincon.core.data.database.converters

import androidx.room.TypeConverter
import dev.juanrincon.core.data.models.TripStatus

class TripStatusConverters {
    @TypeConverter
    fun fromInt(status: TripStatus): Int = status.ordinal

    @TypeConverter
    fun toInt(ordinal: Int): TripStatus = TripStatus.entries[ordinal]
}