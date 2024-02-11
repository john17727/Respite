package dev.juanrincon.respite.presentation.components

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
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.domain.model.Item
import dev.juanrincon.respite.presentation.animations.fadeInFadeOut
import dev.juanrincon.respite.presentation.extensions.startBorder
import dev.juanrincon.respite.presentation.extensions.topBorder

@Composable
fun UserLuggageItem(
    item: Item,
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

@Composable
fun SystemCategoryItem(
    category: Category,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    var showDescription by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
        ) {
            Text(
                text = category.name.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp, start = 4.dp)
            )
            category.description?.let {
                IconButton(
                    onClick = { showDescription = showDescription.not() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
                ) {
                    AnimatedContent(showDescription, transitionSpec = ::fadeInFadeOut) {
                        val icon = if (showDescription) {
                            Icons.Rounded.Close
                        } else {
                            Icons.Rounded.Info
                        }
                        Icon(icon, null)
                    }
                }
            }
        }
        category.description?.let {
            AnimatedVisibility(showDescription) {
                Text(
                    text = it,
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun UserCategoryItem(
    category: Category,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
    ) {
        Text(
            text = category.name.uppercase(),
            style = MaterialTheme.typography.titleLarge,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp, start = 4.dp).fillMaxWidth(0.50f)
        )
        ActionButtons(
            onLeftButtonClick = { onEditClick(category.id) },
            onRightButtonClick = { onDeleteClick(category.id) },
            leftButtonIcon = Icons.Rounded.Edit,
            rightButtonIcon = Icons.Rounded.Delete,
            borderColor = borderColor,
            contentColor = contentColor
        )
    }
}

@Composable
fun EditingCategoryItem(
    category: Category,
    onSave: (Int, String) -> Unit,
    onCancel: (Int) -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = category.name,
                selection = TextRange(category.name.length)
            )
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
    ) {
        RespiteTextField(
            textFieldValue = textFieldValue,
            onValueChange = { if (it.text.length <= 16) textFieldValue = it },
            modifier = Modifier.padding(top = 6.dp, start = 4.dp),
            focusRequester = focusRequester,
            textStyle = MaterialTheme.typography.titleLarge
        )
        ActionButtons(
            onLeftButtonClick = { onCancel(category.id) },
            onRightButtonClick = { onSave(category.id, textFieldValue.text) },
            leftButtonIcon = Icons.Rounded.Close,
            rightButtonIcon = Icons.Rounded.Done,
            rightButtonEnabled = textFieldValue.text.isNotEmpty(),
            contentColor = contentColor,
            borderColor = borderColor,
        )
    }
}

@Composable
fun CreatingCategoryItem(
    category: Category,
    onSave: (String) -> Unit,
    onCancel: () -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = category.name,
                selection = TextRange(category.name.length)
            )
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
    ) {
        RespiteTextField(
            textFieldValue = textFieldValue,
            onValueChange = { if (it.text.length <= 16) textFieldValue = it },
            modifier = Modifier.padding(top = 6.dp, start = 4.dp).fillMaxWidth(0.50f),
            focusRequester = focusRequester,
            textStyle = MaterialTheme.typography.titleLarge
        )
        ActionButtons(
            onLeftButtonClick = onCancel,
            onRightButtonClick = { onSave(textFieldValue.text) },
            leftButtonIcon = Icons.Rounded.Close,
            rightButtonIcon = Icons.Rounded.Done,
            rightButtonEnabled = textFieldValue.text.isNotEmpty(),
            contentColor = contentColor,
            borderColor = borderColor,
        )
    }
}

@Composable
fun ActionButtons(
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    leftButtonIcon: ImageVector,
    rightButtonIcon: ImageVector,
    rightButtonEnabled: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = onLeftButtonClick,
            modifier = Modifier.startBorder(borderColor, 2.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
        ) {
            Icon(leftButtonIcon, null)
        }
        IconButton(
            onClick = onRightButtonClick,
            modifier = Modifier.startBorder(borderColor, 2.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor),
            enabled = rightButtonEnabled
        ) {
            Icon(rightButtonIcon, null)
        }
    }
}