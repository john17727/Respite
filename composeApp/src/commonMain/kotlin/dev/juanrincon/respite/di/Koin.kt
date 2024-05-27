package dev.juanrincon.respite.di

import dev.juanrincon.categories.data.RespiteCategoryRepository
import dev.juanrincon.categories.domain.CategoryRepository
import dev.juanrincon.categories.presentation.CategoriesScreenModel
import dev.juanrincon.core.data.createDatabase
import dev.juanrincon.luggage.data.RespiteLuggageRepository
import dev.juanrincon.luggage.domain.ItemRepository
import dev.juanrincon.luggage.presentation.LuggageScreenModel
import dev.juanrincon.respite.Database
import dev.juanrincon.trips.data.RespiteTripRepository
import dev.juanrincon.trips.domain.TripRepository
import dev.juanrincon.trips.presentation.TripScreenModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        screenModelModule,
        repositoryModule,
        sqlDelightModule,
        platformModule()
    )
}

fun initKoin() = initKoin {}

val screenModelModule = module {
    factory { CategoriesScreenModel(get()) }
    factory { LuggageScreenModel(get(), get()) }
    factory { TripScreenModel(get()) }
}

val repositoryModule = module {
    single<CategoryRepository> { RespiteCategoryRepository(get<Database>().categoryQueries) }
    single<ItemRepository> { RespiteLuggageRepository(get<Database>().itemsQueries) }
    single<TripRepository> { RespiteTripRepository(get<Database>().tripsQueries) }
}

val sqlDelightModule = module {
    single { createDatabase(get()) }
}