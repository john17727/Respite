package dev.juanrincon.categories.presentation.di

import dev.juanrincon.categories.presentation.CategoryViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val categoryViewModelModule: Module = module {
    singleOf(::CategoryViewModel)
}