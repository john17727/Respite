package dev.juanrincon.categories.presentation.di

import dev.juanrincon.categories.presentation.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual val categoryViewModelModule: Module = module {
    viewModelOf(::CategoryViewModel)
}