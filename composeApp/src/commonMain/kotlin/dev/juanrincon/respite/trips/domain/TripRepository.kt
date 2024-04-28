package dev.juanrincon.respite.trips.domain

interface TripRepository {

    suspend fun createTrip(
        name: String,
        status: TripStatus = TripStatus.PackingDestination
    ): Result<Unit>

    suspend fun getCurrentTrip(): Result<Trip?>

    suspend fun updateTrip(trip: Trip): Result<Unit>

    suspend fun upsertItem(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun updateItem(tripId: Int, newItem: TripItem): Result<Unit>

    suspend fun updateAllItems(tripId: Int, newTripId: Int): Result<Unit>

    suspend fun deleteTripOnly(id: Int): Result<Unit>

    suspend fun deleteTripItem(tripId: Int, itemId: Int): Result<Unit>

    suspend fun deleteTripAndItems(id: Int): Result<Unit>
}