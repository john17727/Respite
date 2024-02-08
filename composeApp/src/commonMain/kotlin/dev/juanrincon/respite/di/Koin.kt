package dev.juanrincon.respite.di

import dev.juanrincon.respite.data.createDatabase
import dev.juanrincon.respite.data.repository.RespiteCategoryRepository
import dev.juanrincon.respite.Database
import dev.juanrincon.respite.domain.repository.CategoryRepository
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import dev.juanrincon.respite.presentation.categories.CategoriesScreenModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        sqlDelightModule,
        screenModelModule,
        repositoryModule,
        platformModule()
    )
}

fun initKoin() = initKoin {}

val screenModelModule = module {
    factory { CategoriesScreenModel(get()) }
}

val repositoryModule = module {
    single<CategoryRepository> { RespiteCategoryRepository(get()) }
}

val sqlDelightModule = module {
    single { createDatabase(get()) }
    single { (database: Database) -> database.categoryQueries }
}