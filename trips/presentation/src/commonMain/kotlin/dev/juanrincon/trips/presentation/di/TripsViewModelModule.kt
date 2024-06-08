package dev.juanrincon.trips.presentation.di

import dev.juanrincon.trips.presentation.empty_screen.EmptyViewModel
import dev.juanrincon.trips.presentation.pack_destination.PackForDestinationViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val tripsViewModelModule = module {
    viewModelOf(::EmptyViewModel)
    viewModelOf(::PackForDestinationViewModel)
}