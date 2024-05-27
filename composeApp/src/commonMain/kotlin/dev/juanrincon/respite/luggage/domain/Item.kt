package dev.juanrincon.respite.luggage.domain

import dev.juanrincon.core.domain.Category


data class Item(
    val id: Int,
    val name: String,
    val category: Category
)