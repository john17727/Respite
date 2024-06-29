package dev.juanrincon.trips.presentation.di

import dev.juanrincon.trips.presentation.destination.DestinationViewModel
import dev.juanrincon.trips.presentation.empty_screen.EmptyViewModel
import dev.juanrincon.trips.presentation.pack_destination.PackForDestinationViewModel
import dev.juanrincon.trips.presentation.pack_next_destination.PackForNextDestinationViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val tripsViewModelModule = module {
    viewModelOf(::EmptyViewModel)
    viewModelOf(::PackForDestinationViewModel)
    viewModelOf(::DestinationViewModel)
    viewModelOf(::PackForNextDestinationViewModel)
}