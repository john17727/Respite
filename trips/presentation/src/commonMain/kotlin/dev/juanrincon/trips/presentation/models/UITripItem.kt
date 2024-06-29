package dev.juanrincon.trips.presentation.models

data class UITripItem(
    val id: Int,
    val name: String,
    val category: String,
    val total: Int,
    val packed: Int
)