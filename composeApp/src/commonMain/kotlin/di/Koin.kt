package di

import data.createDatabase
import data.repository.RespiteCategoryRepository
import dev.juanrincon.respite.Database
import domain.repository.CategoryRepository
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import presentation.categories.CategoriesScreenModel

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
}

val repositoryModule = module {
    single<CategoryRepository> { RespiteCategoryRepository(get()) }
}

val sqlDelightModule = module {
    single { createDatabase(get()) }
    single { (database: Database) -> database.categoryQueries }
}