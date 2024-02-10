package dev.juanrincon.respite.domain.repository

import dev.juanrincon.respite.domain.model.Item

interface ItemRespository {
    
    suspend fun create(name: String, categoryId: Int): Result<Unit>

    suspend fun read(): Result<List<Item>>

    suspend fun update(id: Int, name: String, categoryId: Int): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}