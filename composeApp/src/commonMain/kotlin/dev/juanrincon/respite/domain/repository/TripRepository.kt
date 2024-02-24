package dev.juanrincon.respite.domain.repository

import dev.juanrincon.respite.domain.model.Trip

interface TripRepository {

    suspend fun create(trip: Trip): Result<Unit>

    suspend fun readCurrentTrip(): Result<Trip?>

    suspend fun update(trip: Trip): Result<Unit>

    suspend fun delete(id: Int): Result<Unit>
}