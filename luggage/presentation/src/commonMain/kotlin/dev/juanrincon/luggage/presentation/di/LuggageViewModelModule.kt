package dev.juanrincon.luggage.presentation.di

import dev.juanrincon.luggage.presentation.LuggageViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val luggageViewModelModule = module {
    viewModelOf(::LuggageViewModel)
}