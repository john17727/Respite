package dev.juanrincon.luggage.presentation.models

import dev.juanrincon.core.domain.Category

data class UICategory(
    val id: Int,
    val name: String
) {
    companion object {
        inline fun Category.toUICategory(): UICategory = UICategory(
            this.id,
            this.name
        )
    }
}
