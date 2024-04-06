package dev.juanrincon.respite.categories.presentation

data class CategoryState(
    val categories: List<CategoryItem> = listOf(),
    val inEditMode: Boolean = false,
    val inAddMode: Boolean = false,
    val loading: Boolean = false
)
