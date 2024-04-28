package dev.juanrincon.respite.luggage.domain

interface ItemRepository {

    suspend fun create(name: String, categoryId: Int): Result<Unit>

    suspend fun read(): Result<List<Item>>

    suspend fun update(id: Int, name: String, categoryId: Int): Result<Unit>

    suspend fun delete(id: Int): Result<Unit>
}