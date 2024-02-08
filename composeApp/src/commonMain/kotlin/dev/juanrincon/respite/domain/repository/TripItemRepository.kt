package dev.juanrincon.respite.domain.repository

import dev.juanrincon.respite.domain.model.TripItem

interface TripItemRepository {

    suspend fun create(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun read(tripId: Int): Result<List<TripItem>>
    
    suspend fun delete(): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}