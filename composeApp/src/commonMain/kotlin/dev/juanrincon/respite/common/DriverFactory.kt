package dev.juanrincon.respite.common

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import dev.juanrincon.core.domain.TripStatus
import dev.juanrincon.respite.Category
import dev.juanrincon.respite.Database
import dev.juanrincon.respite.Item
import dev.juanrincon.respite.Trip
import dev.juanrincon.respite.Trip_item

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(
        driver = driver,
        categoryAdapter = Category.Adapter(idAdapter = longIntAdapter),
        itemAdapter = Item.Adapter(
            idAdapter = longIntAdapter,
            category_idAdapter = longIntAdapter
        ),
        tripAdapter = Trip.Adapter(
            idAdapter = longIntAdapter,
            statusAdapter = tripStatusStringAdapter,
        ),
        trip_itemAdapter = Trip_item.Adapter(
            trip_idAdapter = longIntAdapter,
            item_idAdapter = longIntAdapter,
            amountAdapter = longIntAdapter,
            accountedAdapter = longIntAdapter
        )
    )
}

private val longIntAdapter = object : ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int = databaseValue.toInt()

    override fun encode(value: Int): Long = value.toLong()

}

private val tripStatusStringAdapter = object : ColumnAdapter<TripStatus, String> {
    override fun decode(databaseValue: String): TripStatus = when (databaseValue) {
        TripStatus.PackingDestination.toString() -> TripStatus.PackingDestination
        TripStatus.Destination.toString() -> TripStatus.Destination
        else -> TripStatus.PackingNextDestination
    }

    override fun encode(value: TripStatus): String = when (value) {
        TripStatus.PackingDestination -> TripStatus.PackingDestination.toString()
        TripStatus.Destination -> TripStatus.Destination.toString()
        TripStatus.PackingNextDestination -> TripStatus.PackingNextDestination.toString()
    }
}
