package dev.juanrincon.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.juanrincon.core.data.database.converters.TripStatusConverters
import dev.juanrincon.core.data.database.daos.CategoryDao
import dev.juanrincon.core.data.database.daos.LuggageItemDao
import dev.juanrincon.core.data.database.daos.TripDao
import dev.juanrincon.core.data.database.daos.TripItemDao
import dev.juanrincon.core.data.database.entities.Category
import dev.juanrincon.core.data.database.entities.Item
import dev.juanrincon.core.data.database.entities.Trip
import dev.juanrincon.core.data.database.entities.TripItem

@Database(entities = [Category::class, Item::class, Trip::class, TripItem::class], version = 1)
@TypeConverters(TripStatusConverters::class)
abstract class RespiteDatabase : RoomDatabase(), DB {
    abstract fun categoryDao(): CategoryDao

    abstract fun itemDao(): LuggageItemDao

    abstract fun tripDao(): TripDao

    abstract fun tripItemDao(): TripItemDao

    override fun clearAllTables() {
        super.clearAllTables()
    }

}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}