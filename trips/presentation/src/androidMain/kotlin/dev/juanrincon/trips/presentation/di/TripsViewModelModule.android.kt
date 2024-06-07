package dev.juanrincon.trips.presentation.di

import dev.juanrincon.trips.presentation.empty_screen.EmptyViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual val tripsViewModelModule: Module = module {
    viewModelOf(::EmptyViewModel)
}