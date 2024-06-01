package dev.juanrincon.categories.domain

import dev.juanrincon.core.domain.Category

interface CategoryRepository {
    
    suspend fun create(newCategory: Category): Result<Unit>

    suspend fun read(): Result<List<Category>>
    
    suspend fun update(category: Category): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}