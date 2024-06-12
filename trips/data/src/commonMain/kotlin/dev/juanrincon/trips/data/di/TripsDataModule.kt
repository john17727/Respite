package dev.juanrincon.trips.data.di

import dev.juanrincon.core.data.database.RespiteDatabase
import dev.juanrincon.trips.data.RoomTripRepository
import dev.juanrincon.trips.domain.TripRepository
import org.koin.dsl.module

val tripsDataModule = module {
//    single<TripRepository> { RespiteTripRepository(get<Database>().tripsQueries) }
    single<TripRepository> {
        RoomTripRepository(
            get<RespiteDatabase>().tripDao(),
            get<RespiteDatabase>().tripItemDao()
        )
    }
}