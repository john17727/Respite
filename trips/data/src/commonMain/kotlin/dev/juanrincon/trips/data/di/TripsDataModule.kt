package dev.juanrincon.trips.data.di

import dev.juanrincon.respite.Database
import dev.juanrincon.trips.data.RespiteTripRepository
import dev.juanrincon.trips.domain.TripRepository
import org.koin.dsl.module

val tripsDataModule = module {
    single<TripRepository> { RespiteTripRepository(get<Database>().tripsQueries) }
}