package dev.juanrincon.respite.common

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.juanrincon.respite.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(Database.Schema, "respite.db")
}