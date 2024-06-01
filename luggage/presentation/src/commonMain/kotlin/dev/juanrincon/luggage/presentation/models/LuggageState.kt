package dev.juanrincon.luggage.presentation

import dev.juanrincon.luggage.presentation.models.UICategory


data class LuggageState(
    val luggage: List<LuggageItem> = listOf(),
    val categories: List<UICategory> = listOf(),
    val inEditMode: Boolean = false,
    val inAddMode: Boolean = false,
    val loading: Boolean = false
)