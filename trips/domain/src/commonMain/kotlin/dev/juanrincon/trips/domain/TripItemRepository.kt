package dev.juanrincon.trips.domain

@Deprecated("This class is deprecated, use TripRepository instead")
interface TripItemRepository {

    suspend fun readAll(tripId: Int): Result<List<TripItem>>

    suspend fun readCurrentTrip(tripId: Int): Result<List<TripItem>>

    suspend fun upsert(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun update(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun updateAll(tripId: Int, newTripId: Int): Result<Unit>

    suspend fun delete(tripId: Int): Result<Unit>
}