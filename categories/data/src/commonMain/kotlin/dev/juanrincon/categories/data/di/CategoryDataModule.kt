package dev.juanrincon.categories.data.di

import dev.juanrincon.categories.data.RoomCategoryRepository
import dev.juanrincon.categories.domain.CategoryRepository
import dev.juanrincon.core.data.database.RespiteDatabase
import org.koin.dsl.module

val categoryDataModule = module {
//    single<CategoryRepository> { RespiteCategoryRepository(get<Database>().categoryQueries) }
    single<CategoryRepository> { RoomCategoryRepository(get<RespiteDatabase>().categoryDao()) }
}