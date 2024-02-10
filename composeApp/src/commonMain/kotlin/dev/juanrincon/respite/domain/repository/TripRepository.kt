package dev.juanrincon.respite.domain.repository

import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.domain.model.TripItem

interface TripRepository {
    
    suspend fun create(trip: Trip): Result<Unit>

    suspend fun read(): Result<List<Trip>>
    
    suspend fun update(trip: Trip): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}