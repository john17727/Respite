package dev.juanrincon.categories.domain

import dev.juanrincon.core.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    
    suspend fun create(newCategory: Category): Result<Unit>

    fun read(): Flow<List<Category>>
    
    suspend fun update(category: Category): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}