package dev.juanrincon.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getRespiteDatabase(context: Context): RespiteDatabase {
    val dbFile = context.getDatabasePath("respite.db")
    return Room.databaseBuilder<RespiteDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver()).build()
}
