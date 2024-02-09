package dev.juanrincon.respite.presentation.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.presentation.components.EditingCategoryItem
import dev.juanrincon.respite.presentation.components.SystemCategoryItem
import dev.juanrincon.respite.presentation.components.UserCategoryItem
import dev.juanrincon.respite.presentation.components.VerticalBanner

@Composable
fun CategoriesUI(
    categories: List<CategoryItem>,
    onEditItem: (Int) -> Unit,
    onDeleteItem: (Int) -> Unit,
    onUpdateItem: (Int, String) -> Unit,
    onSaveItem: (Category) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        VerticalBanner(
            text = "Categories",
            icon = Icons.Rounded.Sell,
            backgroundColor = Color(0xFFA6C994),
            contentColor = Color(0xFF3C422F),
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(28.dp),
            contentPadding = PaddingValues(top = 16.dp, end = 24.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(categories, { item -> item.id }, { item -> item::class }) { item ->
                when (item) {
                    is CategoryItem.SystemItem -> SystemCategoryItem(
                        category = item.category,
                        borderColor = Color(0xFFC2DB9E),
                        contentColor = Color(0xFF3C422F),
                        onInfoClick = {}
                    )

                    is CategoryItem.UserItem -> UserCategoryItem(
                        category = item.category,
                        borderColor = Color(0xFFC2DB9E),
                        contentColor = Color(0xFF3C422F),
                        onEditClick = onEditItem,
                        onDeleteClick = onDeleteItem,
                    )

                    is CategoryItem.EditingItem -> EditingCategoryItem(
                        category = item.category,
                        onNameUpdate = onUpdateItem,
                        onSave = onSaveItem,
                        borderColor = Color(0xFFC2DB9E),
                        contentColor = Color(0xFF3C422F)
                    )
                }
            }
        }
    }
}
