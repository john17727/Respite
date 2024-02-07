package presentation.categories

sealed interface CategoryIntent {
    data object GetAllCategories : CategoryIntent
}