package dev.juanrincon.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.juanrincon.core.data.database.entities.Item
import dev.juanrincon.core.data.database.entities.LuggageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface LuggageItemDao {
    @Transaction
    @Query("SELECT * FROM item")
    fun getAll(): Flow<List<LuggageItem>>

    @Upsert
    suspend fun upsert(item: Item)

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun delete(id: Int)
}