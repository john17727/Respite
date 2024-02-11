package dev.juanrincon.respite.presentation.luggage

data class LuggageState(
    val luggage: List<LuggageItem> = listOf(),
    val inEditMode: Boolean = false,
    val loading: Boolean = false
)