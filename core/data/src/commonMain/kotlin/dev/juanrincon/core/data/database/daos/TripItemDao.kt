package dev.juanrincon.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.juanrincon.core.data.database.entities.DetailTripItem
import dev.juanrincon.core.data.database.entities.PotentialTripItem
import dev.juanrincon.core.data.database.entities.TripItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TripItemDao {
    @Transaction
    @Query(
        "SELECT item.id AS id, item.name AS name, category.name AS category, trip_item.amount AS amount " +
                "FROM item " +
                "JOIN category ON item.category_id = category.id " +
                "LEFT JOIN trip_item ON item.id = trip_item.item_id " +
                "AND trip_item.trip_id = :tripId " +
                "ORDER BY category.name ASC"
    )
    fun getPotentialItemsForTrip(tripId: Int): Flow<List<PotentialTripItem>>

    @Transaction
    @Query("SELECT * FROM trip_item WHERE trip_id = :tripId")
    fun getAllForTrip(tripId: Int): Flow<List<DetailTripItem>>

    @Upsert
    suspend fun upsert(item: TripItem)

    @Query("DELETE FROM trip_item WHERE trip_id = :tripId")
    suspend fun deleteForTrip(tripId: Int)

    @Query("DELETE FROM trip_item WHERE trip_id = :tripId AND item_id = :itemId")
    suspend fun delete(tripId: Int, itemId: Int)
}