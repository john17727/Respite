package presentation.categories

import domain.model.Category

data class CategoryState(
    val categories: List<Category> = listOf(),
    val loading: Boolean = false
)
