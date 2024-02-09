package dev.juanrincon.respite.presentation.categories

data class CategoryState(
    val categories: List<CategoryItem> = listOf(),
    val loading: Boolean = false
)
