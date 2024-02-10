package dev.juanrincon.respite.data

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import dev.juanrincon.respite.Category
import dev.juanrincon.respite.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver, Category.Adapter(idAdapter = longIntAdapter))
}

private val longIntAdapter = object : ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int = databaseValue.toInt()

    override fun encode(value: Int): Long = value.toLong()

}