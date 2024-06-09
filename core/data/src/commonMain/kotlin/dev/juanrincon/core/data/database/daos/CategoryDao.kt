package dev.juanrincon.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.juanrincon.core.data.database.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<Category>>

    @Upsert
    suspend fun upsert(category: Category)

    @Query("DELETE FROM category WHERE id = :id")
    suspend fun delete(id: Int)
}