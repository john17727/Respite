package dev.juanrincon.trips.domain

data class TripItem(
    val id: Int,
    val name: String,
    val category: String,
    val amount: Int,
    val accounted: Int
) {
    fun increment() = copy(amount = amount + 1)
    fun decrement() = copy(amount = amount - 1)
}