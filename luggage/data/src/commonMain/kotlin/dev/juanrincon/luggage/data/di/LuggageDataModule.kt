package dev.juanrincon.luggage.data.di

import dev.juanrincon.core.data.database.RespiteDatabase
import dev.juanrincon.luggage.data.RoomLuggageRepository
import dev.juanrincon.luggage.domain.ItemRepository
import org.koin.dsl.module

val luggageDataModule = module {
//    single<ItemRepository> { RespiteLuggageRepository(get<Database>().itemsQueries) }
    single<ItemRepository> { RoomLuggageRepository(get<RespiteDatabase>().itemDao()) }
}