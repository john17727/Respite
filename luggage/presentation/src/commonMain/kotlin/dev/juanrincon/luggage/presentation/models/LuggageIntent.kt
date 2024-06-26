package dev.juanrincon.luggage.presentation

sealed interface LuggageIntent {
    data class UpdateLuggage(val id: Int, val name: String, val categoryId: Int) : LuggageIntent
    data class DeleteLuggage(val id: Int) : LuggageIntent
    data class CreateLuggage(val name: String, val categoryId: Int) : LuggageIntent
    data class EditItem(val id: Int) : LuggageIntent
    data class CancelEditItem(val id: Int) : LuggageIntent
    data object CreateItem : LuggageIntent
    data object CancelCreateItem : LuggageIntent
    data object NavigateBack : LuggageIntent
}