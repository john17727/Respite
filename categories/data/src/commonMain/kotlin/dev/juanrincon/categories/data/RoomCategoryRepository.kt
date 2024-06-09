package dev.juanrincon.categories.data

import dev.juanrincon.categories.data.utils.toDomain
import dev.juanrincon.categories.data.utils.toEntity
import dev.juanrincon.categories.domain.CategoryRepository
import dev.juanrincon.core.data.database.CategoryDao
import dev.juanrincon.core.domain.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository {
    override suspend fun create(newCategory: Category): Result<Unit> = try {
        Result.success(categoryDao.upsert(newCategory.toEntity()))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override fun read(): Flow<List<Category>> =
        categoryDao.getAll().map { categories -> categories.map { it.toDomain() } }

    override suspend fun update(category: Category): Result<Unit> = try {
        Result.success(categoryDao.upsert(category.toEntity()))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun delete(id: Int): Result<Unit> = try {
        Result.success(categoryDao.delete(id))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}