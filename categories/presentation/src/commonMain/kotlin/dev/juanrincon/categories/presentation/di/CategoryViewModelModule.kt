package dev.juanrincon.categories.presentation.di

import dev.juanrincon.categories.presentation.CategoryViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val categoryViewModelModule = module {
    viewModelOf(::CategoryViewModel)
}