package dev.juanrincon.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.juanrincon.core.data.database.entities.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trip WHERE current = true")
    fun getCurrentTrip(): Flow<Trip?>

    @Query("SELECT * FROM trip WHERE id = :id")
    fun getTrip(id: Int): Flow<Trip?>

    @Upsert
    suspend fun upsert(trip: Trip): Long

    @Query("DELETE FROM trip WHERE id = :id")
    suspend fun delete(id: Int)
}