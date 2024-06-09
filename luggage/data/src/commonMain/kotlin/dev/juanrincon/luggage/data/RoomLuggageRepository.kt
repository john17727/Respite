package dev.juanrincon.luggage.data

import dev.juanrincon.core.data.database.daos.LuggageItemDao
import dev.juanrincon.luggage.data.utils.toDomain
import dev.juanrincon.luggage.domain.Item
import dev.juanrincon.luggage.domain.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.juanrincon.core.data.database.entities.Item as ItemEntity

internal class RoomLuggageRepository(
    private val luggageDao: LuggageItemDao
) : ItemRepository {
    override suspend fun create(name: String, categoryId: Int): Result<Unit> = try {
        Result.success(luggageDao.upsert(ItemEntity(id = 0, name = name, categoryId = categoryId)))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override fun read(): Flow<List<Item>> =
        luggageDao.getAll().map { luggage -> luggage.map { it.toDomain() } }


    override suspend fun update(id: Int, name: String, categoryId: Int): Result<Unit> = try {
        Result.success(luggageDao.upsert(ItemEntity(id = id, name = name, categoryId = categoryId)))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun delete(id: Int): Result<Unit> = try {
        Result.success(luggageDao.delete(id))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}