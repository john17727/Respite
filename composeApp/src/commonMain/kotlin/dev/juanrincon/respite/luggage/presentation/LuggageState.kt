package dev.juanrincon.respite.luggage.presentation

import dev.juanrincon.core.domain.Category


data class LuggageState(
    val luggage: List<LuggageItem> = listOf(),
    val categories: List<Category> = listOf(),
    val inEditMode: Boolean = false,
    val inAddMode: Boolean = false,
    val loading: Boolean = false
)