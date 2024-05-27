package dev.juanrincon.respite.common.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.domain.Category
import dev.juanrincon.core.presentation.components.ActionButtons
import dev.juanrincon.core.presentation.components.RespiteTextField
import dev.juanrincon.core.presentation.extensions.topBorder
import dev.juanrincon.respite.luggage.domain.Item

@Composable
fun UserLuggageItem(
    item: Item,
    inEditMode: Boolean,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
        ) {
            Text(
                text = item.name.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp, start = 4.dp).fillMaxWidth(0.50f)
            )
            ActionButtons(
                onLeftButtonClick = { onEditClick(item.id) },
                onRightButtonClick = { onDeleteClick(item.id) },
                leftButtonIcon = Icons.Rounded.Edit,
                rightButtonIcon = Icons.Rounded.Delete,
                rightButtonEnabled = inEditMode.not(),
                leftButtonEnabled = inEditMode.not(),
                borderColor = borderColor,
                contentColor = contentColor
            )
        }
        Text(
            text = item.category.name.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.topBorder(borderColor, 2.dp).padding(top = 6.dp, start = 4.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun EditingLuggageItem(
    item: Item,
    categories: List<Category>,
    onCancel: (Int) -> Unit,
    onSave: (Int, String, Int) -> Unit,
    onExpanded: (Long) -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = item.name,
                selection = TextRange(item.name.length)
            )
        )
    }
    var selectedCategory by remember { mutableStateOf(item.category) }
    var showCategoryMenu by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
        ) {
            RespiteTextField(
                textFieldValue = textFieldValue,
                onValueChange = { if (it.text.length <= 16) textFieldValue = it },
                modifier = Modifier.padding(top = 6.dp, start = 4.dp),
                focusRequester = focusRequester,
                textStyle = MaterialTheme.typography.titleLarge
            )
            ActionButtons(
                onLeftButtonClick = { onCancel(item.id) },
                onRightButtonClick = { onSave(item.id, textFieldValue.text, selectedCategory.id) },
                leftButtonIcon = Icons.Rounded.Close,
                rightButtonIcon = Icons.Rounded.Done,
                rightButtonEnabled = textFieldValue.text.isNotEmpty(),
                borderColor = borderColor,
                contentColor = contentColor
            )
        }
        Row(
            modifier = Modifier.topBorder(borderColor, 2.dp)
                .padding(top = 6.dp, start = 4.dp)
                .fillMaxWidth().clickable(onClick = {
                    showCategoryMenu = showCategoryMenu.not()
                    if (showCategoryMenu) {
                        onExpanded(200)
                    }
                })
        ) {
            Text(
                text = AnnotatedString(selectedCategory.name.uppercase()),
                style = MaterialTheme.typography.titleMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            AnimatedContent(showCategoryMenu) {
                if (it) {
                    Icon(Icons.Rounded.ExpandLess, null, tint = contentColor)
                } else {
                    Icon(Icons.Rounded.ExpandMore, null, tint = contentColor)
                }
            }
        }
        AnimatedVisibility(showCategoryMenu) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 6.dp, start = 4.dp),
                modifier = Modifier.heightIn(max = 200.dp)
            ) {
                items(categories, { item -> item.id }) { category ->
                    ClickableText(
                        text = AnnotatedString(category.name.uppercase()),
                        style = MaterialTheme.typography.titleMedium,
                        onClick = {
                            selectedCategory = category
                            showCategoryMenu = showCategoryMenu.not()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CreatingLuggageItem(
    categories: List<Category>,
    onCancel: () -> Unit,
    onSave: (String, Int) -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var showCategoryMenu by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
        ) {
            RespiteTextField(
                textFieldValue = textFieldValue,
                onValueChange = { if (it.text.length <= 16) textFieldValue = it },
                modifier = Modifier.padding(top = 6.dp, start = 4.dp),
                focusRequester = focusRequester,
                textStyle = MaterialTheme.typography.titleLarge
            )
            ActionButtons(
                onLeftButtonClick = onCancel,
                onRightButtonClick = { onSave(textFieldValue.text, selectedCategory.id) },
                leftButtonIcon = Icons.Rounded.Close,
                rightButtonIcon = Icons.Rounded.Done,
                rightButtonEnabled = textFieldValue.text.isNotEmpty(),
                borderColor = borderColor,
                contentColor = contentColor
            )
        }
        Row(
            modifier = Modifier.topBorder(borderColor, 2.dp)
                .padding(top = 6.dp, start = 4.dp)
                .fillMaxWidth().clickable(onClick = { showCategoryMenu = showCategoryMenu.not() })
        ) {
            Text(
                text = AnnotatedString(selectedCategory.name.uppercase()),
                style = MaterialTheme.typography.titleMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            AnimatedContent(showCategoryMenu) {
                if (it) {
                    Icon(Icons.Rounded.ExpandLess, null, tint = contentColor)
                } else {
                    Icon(Icons.Rounded.ExpandMore, null, tint = contentColor)
                }
            }
        }
        AnimatedVisibility(showCategoryMenu) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 6.dp, start = 4.dp),
                modifier = Modifier.heightIn(max = 200.dp)
            ) {
                items(categories, { item -> item.id }) { category ->
                    ClickableText(
                        text = AnnotatedString(category.name.uppercase()),
                        style = MaterialTheme.typography.titleMedium,
                        onClick = {
                            selectedCategory = category
                            showCategoryMenu = showCategoryMenu.not()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}