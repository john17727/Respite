package dev.juanrincon.respite.data.repository

import dev.juanrincon.respite.TripsQueries
import dev.juanrincon.respite.domain.model.TripItem
import dev.juanrincon.respite.domain.repository.TripItemRepository

class RespiteTripItemRepository(private val tripsQueries: TripsQueries) : TripItemRepository {
    override suspend fun readAll(tripId: Int): Result<List<TripItem>> = try {
        Result.success(tripsQueries.getAllTripItems(tripId) { id, name, category, amount, accounted ->
            TripItem(
                id,
                name,
                category,
                amount ?: 0,
                accounted ?: 0
            )
        }.executeAsList())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun readCurrentTrip(tripId: Int): Result<List<TripItem>> = try {
        Result.success(tripsQueries.getCurrentTripItems(tripId) { id, name, category, amount, accounted ->
            TripItem(
                id,
                name,
                category,
                amount,
                accounted
            )
        }.executeAsList())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun upsert(tripId: Int, newItem: TripItem): Result<Unit> = try {
        tripsQueries.upsertTripItem(
            id = getTripItemKey(tripId, newItem.id),
            trip_id = tripId,
            item_id = newItem.id,
            amount = newItem.amount,
            accounted = newItem.accounted
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun update(tripId: Int, newItem: TripItem): Result<Unit> = try {
        tripsQueries.updateTripItem(
            amount = newItem.amount,
            accounted = newItem.accounted,
            id = getTripItemKey(tripId, newItem.id)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateAll(tripId: Int, newTripId: Int): Result<Unit> = try {
        tripsQueries.updateTripItems(tripId, newTripId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun delete(tripId: Int): Result<Unit> = try {
        Result.success(tripsQueries.deleteTripItems(tripId))
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun getTripItemKey(tripId: Int, itemId: Int): String = "$tripId-$itemId"
}