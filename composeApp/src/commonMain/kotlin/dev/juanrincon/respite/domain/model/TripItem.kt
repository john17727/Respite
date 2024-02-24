package dev.juanrincon.respite.domain.model

data class TripItem(
    val id: Int,
    val name: String,
    val category: String,
    val amount: Int,
    val accounted: Int
)