package dev.juanrincon.respite.di

import dev.juanrincon.categories.data.di.categoryDataModule
import dev.juanrincon.categories.presentation.di.categoryViewModelModule
import dev.juanrincon.core.data.createDatabase
import dev.juanrincon.luggage.data.di.luggageDataModule
import dev.juanrincon.luggage.presentation.di.luggageViewModelModule
import dev.juanrincon.trips.data.di.tripsDataModule
import dev.juanrincon.trips.presentation.di.tripsViewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        sqlDelightModule,
        platformModule(),
        tripsDataModule,
        tripsViewModelModule,
        categoryDataModule,
        categoryViewModelModule,
        luggageDataModule,
        luggageViewModelModule
    )
}

fun initKoin() = initKoin {}

val sqlDelightModule = module {
    single { createDatabase(get()) }
}