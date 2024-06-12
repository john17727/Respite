package dev.juanrincon.core.data.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory


fun getRespiteDatabase(): RespiteDatabase {
    val dbFile = NSHomeDirectory() + "/respite.db"
    return Room.databaseBuilder<RespiteDatabase>(
        name = dbFile,
        factory = { RespiteDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .build()
}
