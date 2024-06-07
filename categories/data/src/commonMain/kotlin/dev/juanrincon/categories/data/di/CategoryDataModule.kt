package dev.juanrincon.categories.data.di

import dev.juanrincon.categories.data.RespiteCategoryRepository
import dev.juanrincon.categories.domain.CategoryRepository
import dev.juanrincon.respite.Database
import org.koin.dsl.module

val categoryDataModule = module {
    single<CategoryRepository> { RespiteCategoryRepository(get<Database>().categoryQueries) }
}