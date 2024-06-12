package dev.juanrincon.trips.domain

import dev.juanrincon.core.domain.TripStatus
import kotlinx.coroutines.flow.Flow

interface TripRepository {

    suspend fun createTrip(
        name: String,
        status: TripStatus = TripStatus.PackingDestination
    ): Result<Int>

    fun getCurrentTrip(): Flow<Trip?>

    fun getPotentialItemsForTrip(tripId: Int): Flow<List<TripItem>>

    fun getItemsForTrip(tripId: Int): Flow<List<TripItem>>

    suspend fun updateTrip(trip: Trip): Result<Unit>

    suspend fun upsertItem(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun updateItem(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun deleteTripOnly(id: Int): Result<Unit>

    suspend fun deleteTripItem(tripId: Int, itemId: Int): Result<Unit>

    suspend fun deleteTripAndItems(id: Int): Result<Unit>
}