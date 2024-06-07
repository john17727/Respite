package dev.juanrincon.luggage.presentation.di

import dev.juanrincon.luggage.presentation.LuggageViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val luggageViewModelModule: Module = module {
    singleOf(::LuggageViewModel)
}