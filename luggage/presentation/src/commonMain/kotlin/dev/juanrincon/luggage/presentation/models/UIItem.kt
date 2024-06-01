package dev.juanrincon.luggage.presentation.models

import dev.juanrincon.luggage.domain.Item
import dev.juanrincon.luggage.presentation.models.UICategory.Companion.toUICategory

class UIItem(
    val id: Int,
    val name: String,
    val category: UICategory
) {
    companion object {
        inline fun Item.toUIItem() = UIItem(
            this.id,
            this.name,
            this.category.toUICategory()
        )
    }
}