package dev.juanrincon.respite.presentation.categories

sealed interface CategoryIntent {
    data object GetAllCategories : CategoryIntent
}