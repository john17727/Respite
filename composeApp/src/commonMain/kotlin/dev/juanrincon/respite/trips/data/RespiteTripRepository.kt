package dev.juanrincon.respite.trips.data

import dev.juanrincon.respite.TripsQueries
import dev.juanrincon.respite.trips.domain.Trip
import dev.juanrincon.respite.trips.domain.TripItem
import dev.juanrincon.respite.trips.domain.TripRepository
import dev.juanrincon.respite.trips.domain.TripStatus
import dev.juanrincon.respite.trips.domain.extensions.getCityAbbreviation

class RespiteTripRepository(private val tripsQueries: TripsQueries) : TripRepository {
    override suspend fun createTrip(name: String, status: TripStatus): Result<Unit> = try {
        tripsQueries.insertTrip(
            id = null,
            name = name,
            status = status,
            current = true
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCurrentTrip(): Result<Trip?> = try {
        val trip = tripsQueries.getCurrentTrip().executeAsOneOrNull()?.let {
            val items: List<TripItem> = if (it.status is TripStatus.PackingDestination) {
                tripsQueries.getAllTripItems<TripItem>(it.id) { id, name, category, amount, accounted ->
                    TripItem(
                        id,
                        name,
                        category,
                        amount ?: 0,
                        accounted ?: 0
                    )
                }.executeAsList()
            } else {
                tripsQueries.getCurrentTripItems<TripItem>(it.id) { id, name, category, amount, accounted ->
                    TripItem(
                        id,
                        name,
                        category,
                        amount,
                        accounted
                    )
                }.executeAsList()
            }
            Trip(
                it.id,
                it.name,
                it.name.getCityAbbreviation(),
                it.status,
                it.current,
                items
            )
        }
        Result.success(trip)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateTrip(trip: Trip): Result<Unit> = try {
        tripsQueries.updateTrip(
            id = trip.id,
            name = trip.name,
            status = trip.status,
            current = trip.current
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun upsertItem(tripId: Int, newItem: TripItem): Result<Unit> = try {
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

    override suspend fun updateItem(tripId: Int, newItem: TripItem): Result<Unit> = try {
        tripsQueries.updateTripItem(
            amount = newItem.amount,
            accounted = newItem.accounted,
            id = getTripItemKey(tripId, newItem.id)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateAllItems(tripId: Int, newTripId: Int): Result<Unit> = try {
        tripsQueries.updateTripItems(tripId, newTripId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTripOnly(id: Int): Result<Unit> = try {
        Result.success(tripsQueries.deleteTrip(id))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTripItem(tripId: Int, itemId: Int): Result<Unit> = try {
        Result.success(tripsQueries.deleteTripItem(getTripItemKey(tripId, itemId)))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTripAndItems(id: Int): Result<Unit> = try {
        deleteTripOnly(id)
        Result.success(tripsQueries.deleteTripItems(id))
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun getTripItemKey(tripId: Int, itemId: Int): String = "$tripId-$itemId"
}