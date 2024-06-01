package dev.juanrincon.categories.presentation.models

import dev.juanrincon.core.domain.Category

data class UICategory(
    val id: Int,
    val name: String,
    val description: String?
) {
    companion object {
        inline fun Category.toUIModel() = UICategory(
            id = id,
            name = name,
            description = description
        )
    }
}
