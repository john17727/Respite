package dev.juanrincon.respite.di

import dev.juanrincon.respite.Database
import dev.juanrincon.respite.data.createDatabase
import dev.juanrincon.respite.data.repository.RespiteCategoryRepository
import dev.juanrincon.respite.data.repository.RespiteLuggageRepository
import dev.juanrincon.respite.domain.repository.CategoryRepository
import dev.juanrincon.respite.domain.repository.ItemRepository
import dev.juanrincon.respite.presentation.categories.CategoriesScreenModel
import dev.juanrincon.respite.presentation.luggage.LuggageScreenModel
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
    factory { LuggageScreenModel(get()) }
}

val repositoryModule = module {
    single<CategoryRepository> { RespiteCategoryRepository(get<Database>().categoryQueries) }
    single<ItemRepository> { RespiteLuggageRepository(get<Database>().itemsQueries) }
}

val sqlDelightModule = module {
    single { createDatabase(get()) }
}