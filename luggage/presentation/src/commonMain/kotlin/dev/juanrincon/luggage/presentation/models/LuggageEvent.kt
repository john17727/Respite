package dev.juanrincon.luggage.presentation.models

sealed interface LuggageEvent {
    data object NavigateBack : LuggageEvent
}