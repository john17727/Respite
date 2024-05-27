package dev.juanrincon.respite.luggage.data

import dev.juanrincon.core.domain.Category
import dev.juanrincon.respite.ItemsQueries
import dev.juanrincon.respite.luggage.domain.Item
import dev.juanrincon.respite.luggage.domain.ItemRepository

class RespiteLuggageRepository(private val itemsQueries: ItemsQueries) : ItemRepository {
    override suspend fun create(name: String, categoryId: Int): Result<Unit> = try {
        Result.success(itemsQueries.insert(null, name, categoryId))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun read(): Result<List<Item>> = try {
        Result.success(itemsQueries.get { id, name, categoryId, categoryName ->
            Item(
                id,
                name,
                Category(categoryId, categoryName, null)
            )
        }.executeAsList())
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun update(id: Int, name: String, categoryId: Int): Result<Unit> = try {
        Result.success(itemsQueries.update(name, categoryId, id))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun delete(id: Int): Result<Unit> = try {
        Result.success(itemsQueries.delete(id))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}