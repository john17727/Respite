package dev.juanrincon.trips.domain

data class TripItem(
    val id: Int,
    val name: String,
    val category: String,
    val total: Int,
    val packed: Int
) {
    fun incrementTotal() = copy(total = total + 1)
    fun decrementTotal() = copy(total = total - 1)
    fun incrementPacked() = copy(packed = packed + 1)
    fun decrementPacked() = copy(packed = packed - 1)
}