package dev.juanrincon.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.juanrincon.core.data.database.daos.CategoryDao
import dev.juanrincon.core.data.database.daos.LuggageItemDao
import dev.juanrincon.core.data.database.entities.Category
import dev.juanrincon.core.data.database.entities.Item

@Database(entities = [Category::class, Item::class], version = 1)
abstract class RespiteDatabase : RoomDatabase(), DB {
    abstract fun categoryDao(): CategoryDao

    abstract fun itemDao(): LuggageItemDao

    override fun clearAllTables() {
        super.clearAllTables()
    }

}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}