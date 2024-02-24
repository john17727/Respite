package dev.juanrincon.respite.domain.repository

import dev.juanrincon.respite.domain.model.TripItem

interface TripItemRepository {

    suspend fun readAll(tripId: Int): Result<List<TripItem>>

    suspend fun readCurrentTrip(tripId: Int): Result<List<TripItem>>

    suspend fun upsert(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun update(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun updateAll(tripId: Int, newTripId: Int): Result<Unit>

    suspend fun delete(tripId: Int): Result<Unit>
}