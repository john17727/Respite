package dev.juanrincon.respite.data.repository

import dev.juanrincon.respite.TripsQueries
import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.domain.repository.TripRepository

class RespiteTripRepository(private val tripsQueries: TripsQueries) : TripRepository {
    override suspend fun create(trip: Trip): Result<Unit> = try {
        tripsQueries.insertTrip(
            id = null,
            name = trip.name,
            status = trip.status,
            current = trip.current
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun read(): Result<List<Trip>> = try {
        Result.success(tripsQueries.getTrip { id, name, status, current ->
            Trip(
                id,
                name,
                status,
                current
            )
        }.executeAsList())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun update(trip: Trip): Result<Unit> = try {
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

    override suspend fun delete(id: Int): Result<Unit> = try {
        Result.success(tripsQueries.deleteTrip(id))
    } catch (e: Exception) {
        Result.failure(e)
    }
}