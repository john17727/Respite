package dev.juanrincon.luggage.data.di

import dev.juanrincon.luggage.data.RespiteLuggageRepository
import dev.juanrincon.luggage.domain.ItemRepository
import dev.juanrincon.respite.Database
import org.koin.dsl.module

val luggageDataModule = module {
    single<ItemRepository> { RespiteLuggageRepository(get<Database>().itemsQueries) }
}