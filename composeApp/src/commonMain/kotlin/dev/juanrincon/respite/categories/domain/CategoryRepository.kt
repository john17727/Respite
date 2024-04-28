package dev.juanrincon.respite.categories.domain

interface CategoryRepository {
    
    suspend fun create(newCategory: Category): Result<Unit>

    suspend fun read(): Result<List<Category>>
    
    suspend fun update(category: Category): Result<Unit>
    
    suspend fun delete(id: Int): Result<Unit>
}