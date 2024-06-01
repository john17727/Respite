package dev.juanrincon.trips.domain

data class TripItem(
    val id: Int,
    val name: String,
    val category: String,
    val total: Int
) {
    fun increment() = copy(total = total + 1)
    fun decrement() = copy(total = total - 1)
}