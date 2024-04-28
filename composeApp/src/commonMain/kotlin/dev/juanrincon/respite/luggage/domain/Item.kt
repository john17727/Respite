package dev.juanrincon.respite.luggage.domain

import dev.juanrincon.respite.categories.domain.Category

data class Item(
    val id: Int,
    val name: String,
    val category: Category
)