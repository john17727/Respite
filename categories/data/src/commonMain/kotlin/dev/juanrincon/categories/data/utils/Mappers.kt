package dev.juanrincon.categories.data.utils

import dev.juanrincon.core.domain.Category
import dev.juanrincon.core.data.database.Category as CategoryEntity

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    this.id,
    this.name,
    this.description
)

fun CategoryEntity.toDomain(): Category = Category(
    this.id,
    this.name,
    this.description
)