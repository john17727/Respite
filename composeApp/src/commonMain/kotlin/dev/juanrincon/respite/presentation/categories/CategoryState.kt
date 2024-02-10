package dev.juanrincon.respite.presentation.categories

data class CategoryState(
    val categories: List<CategoryItem> = listOf(),
    val inEditMode: Boolean = false,
    val loading: Boolean = false
)
