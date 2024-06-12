package dev.juanrincon.trips.data

import dev.juanrincon.core.data.database.daos.TripDao
import dev.juanrincon.core.data.database.daos.TripItemDao
import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.trips.data.utils.toDomain
import dev.juanrincon.trips.data.utils.toEntity
import dev.juanrincon.trips.domain.Trip
import dev.juanrincon.trips.domain.TripItem
import dev.juanrincon.trips.domain.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.juanrincon.core.data.database.entities.Trip as TripEntity
import dev.juanrincon.core.data.database.entities.TripItem as TripItemEntity

internal class RoomTripRepository(
    private val tripDao: TripDao,
    private val tripItemDao: TripItemDao
) :
    TripRepository {
    override suspend fun createTrip(name: String, status: TripStatus): Result<Int> = try {
        Result.success(
            tripDao.upsert(
                TripEntity(
                    id = 0,
                    name = name,
                    status = status.toEntity(),
                    current = true
                )
            ).toInt()
        )
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override fun getCurrentTrip(): Flow<Trip?> =
        tripDao.getCurrentTrip().map { trip -> trip?.toDomain() }

    override fun getPotentialItemsForTrip(tripId: Int): Flow<List<TripItem>> =
        tripItemDao.getPotentialItemsForTrip(tripId).map { items ->
            items.map { it.toDomain() }
        }

    override fun getItemsForTrip(tripId: Int): Flow<List<TripItem>> =
        tripItemDao.getAllForTrip(tripId).map { items -> items.map { it.toDomain() } }

    override suspend fun updateTrip(trip: Trip): Result<Unit> = try {
        tripDao.upsert(trip.toEntity())
        Result.success(Unit)
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun upsertItem(tripId: Int, newItem: TripItem): Result<Unit> = try {
        Result.success(tripItemDao.upsert(TripItemEntity(tripId, newItem.id, newItem.total)))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun updateItem(tripId: Int, newItem: TripItem): Result<Unit> = try {
        Result.success(tripItemDao.upsert(TripItemEntity(tripId, newItem.id, newItem.total)))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun deleteTripOnly(id: Int): Result<Unit> = try {
        Result.success(tripDao.delete(id))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun deleteTripItem(tripId: Int, itemId: Int): Result<Unit> = try {
        Result.success(tripItemDao.delete(tripId, itemId))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun deleteTripAndItems(id: Int): Result<Unit> = try {
        tripDao.delete(id)
        tripItemDao.deleteForTrip(id)
        Result.success(Unit)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}