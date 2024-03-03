package dev.juanrincon.respite.domain.model

data class Trip(
    val id: Int,
    val name: String,
    val status: TripStatus,
    val current: Boolean,
    val items: List<TripItem>
)

sealed interface TripStatus {
    data object PackingDestination : TripStatus
    data object Destination : TripStatus
    data object PackingNextDestination : TripStatus
}