package dev.juanrincon.categories.presentation.models

sealed class CategoryEvent {
    data object NavigateBack : CategoryEvent()
}