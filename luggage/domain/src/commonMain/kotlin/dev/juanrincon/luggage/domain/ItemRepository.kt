package dev.juanrincon.luggage.domain

import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun create(name: String, categoryId: Int): Result<Unit>

    fun read(): Flow<List<Item>>

    suspend fun update(id: Int, name: String, categoryId: Int): Result<Unit>

    suspend fun delete(id: Int): Result<Unit>
}