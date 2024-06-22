package dev.juanrincon.luggage.presentation.models

import dev.juanrincon.luggage.domain.Item
import dev.juanrincon.luggage.presentation.models.UICategory.Companion.toUICategory

data class UIItem(
    val id: Int,
    val name: String,
    val category: UICategory
) {
    companion object {
        fun Item.toUIItem() = UIItem(
            this.id,
            this.name,
            this.category.toUICategory()
        )
    }
}