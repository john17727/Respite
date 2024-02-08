package dev.juanrincon.respite.domain.repository

import dev.juanrincon.respite.domain.model.Item

interface ItemRespository {
    
    suspend fun create(newItem: Item): Result<Unit>

    suspend fun read(): Result<List<Item>>
    
    suspend fun update(item: Item): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}