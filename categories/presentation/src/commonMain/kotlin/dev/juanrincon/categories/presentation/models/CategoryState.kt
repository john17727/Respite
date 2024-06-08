package dev.juanrincon.categories.presentation.models

data class CategoryState(
    val categories: List<CategoryItem> = listOf(),
    val inEditMode: Boolean = false,
    val inAddMode: Boolean = false,
    val loading: Boolean = false,
    val transitionAnimation: Boolean = false,
    val listAnimation: Boolean = false,
)
