package dev.juanrincon.luggage.data

import dev.juanrincon.core.domain.Category
import dev.juanrincon.luggage.domain.Item
import dev.juanrincon.luggage.domain.ItemRepository
import dev.juanrincon.respite.ItemsQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class RespiteLuggageRepository(private val itemsQueries: ItemsQueries) : ItemRepository {
    override suspend fun create(name: String, categoryId: Int): Result<Unit> = try {
        Result.success(itemsQueries.insert(null, name, categoryId))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override fun read(): Flow<List<Item>> = flowOf(
        itemsQueries.get { id, name, categoryId, categoryName ->
            Item(
                id,
                name,
                Category(categoryId, categoryName, null)
            )
        }.executeAsList()
    )

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