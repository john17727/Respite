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
    fun incrementPacked() = if (packed < total) copy(packed = packed + 1) else this
    fun decrementPacked() = if (packed > 0) copy(packed = packed - 1) else this
}