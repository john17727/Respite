package dev.juanrincon.respite.di

import dev.juanrincon.core.data.database.RespiteDatabase
import dev.juanrincon.core.data.database.getRespiteDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<RespiteDatabase> { getRespiteDatabase() }
}