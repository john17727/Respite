package dev.juanrincon.respite.presentation.categories

import dev.juanrincon.respite.domain.model.Category

data class CategoryState(
    val categories: List<Category> = listOf(),
    val loading: Boolean = false
)
