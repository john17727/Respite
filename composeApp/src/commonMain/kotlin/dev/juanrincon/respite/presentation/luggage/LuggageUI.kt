package dev.juanrincon.respite.presentation.luggage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.presentation.components.BannerAlignment
import dev.juanrincon.respite.presentation.components.EditingLuggageItem
import dev.juanrincon.respite.presentation.components.UserLuggageItem
import dev.juanrincon.respite.presentation.components.VerticalBanner

@Composable
fun LuggageUI(
    luggage: List<LuggageItem>,
    categories: List<Category>,
    onDeleteClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onEditSave: (Int, String, Int) -> Unit,
    onEditCancel: (Int) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    Scaffold(
        floatingActionButton = {

        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(28.dp),
                contentPadding = PaddingValues(top = 16.dp, start = 24.dp),
                modifier = Modifier.fillMaxHeight().weight(1f)
            ) {
                items(luggage, { item -> item.id }, { item -> item::class }) { item ->
                    when (item) {
                        is LuggageItem.CreatingItem -> Text(item.item.name)
                        is LuggageItem.EditingItem -> EditingLuggageItem(
                            item = item.item,
                            categories = categories,
                            onCancel = onEditCancel,
                            onSave = onEditSave,
                            focusRequester = focusRequester,
                            borderColor = Color(0xFFFFD55F),
                            contentColor = Color(0xFF684633),
                        )

                        is LuggageItem.UserItem -> UserLuggageItem(
                            item = item.item,
                            borderColor = Color(0xFFFFD55F),
                            contentColor = Color(0xFF684633),
                            onEditClick = onEditClick,
                            onDeleteClick = onDeleteClick
                        )
                    }
                }
            }
            VerticalBanner(
                text = "Luggage",
                icon = Icons.Rounded.Luggage,
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                alignment = BannerAlignment.End
            )
        }
    }
}