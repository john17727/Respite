package dev.juanrincon.respite.di

import dev.juanrincon.respite.data.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DriverFactory(get()) }
}