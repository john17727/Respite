package dev.juanrincon.categories.data

import dev.juanrincon.categories.domain.CategoryRepository
import dev.juanrincon.core.domain.Category
import dev.juanrincon.respite.CategoryQueries


class RespiteCategoryRepository(private val categoryQueries: CategoryQueries): CategoryRepository {
    override suspend fun create(newCategory: Category): Result<Unit> = try {
        Result.success(categoryQueries.insert(null, newCategory.name, newCategory.description))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun read(): Result<List<Category>> = try {
        Result.success(categoryQueries.get{ id: Int, name: String, description: String? -> Category(id, name, description) }.executeAsList())
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun update(category: Category): Result<Unit> = try {
        Result.success(categoryQueries.update(category.name, category.id))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun delete(id: Int): Result<Unit> = try {
        Result.success(categoryQueries.delete(id))
    } catch (t: Throwable) {
        Result.failure(t)
    }

}