package dev.juanrincon.categories.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.juanrincon.categories.presentation.models.UICategory
import dev.juanrincon.core.presentation.animations.fadeInFadeOut
import dev.juanrincon.core.presentation.components.ActionButtons
import dev.juanrincon.core.presentation.components.RespiteTextField
import dev.juanrincon.core.presentation.extensions.topBorder

@Composable
internal fun SystemCategoryItem(
    category: UICategory,
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
internal fun UserCategoryItem(
    category: UICategory,
    inEditMode: Boolean,
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
            rightButtonEnabled = inEditMode.not(),
            leftButtonEnabled = inEditMode.not(),
            borderColor = borderColor,
            contentColor = contentColor
        )
    }
}

@Composable
internal fun EditingCategoryItem(
    category: UICategory,
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
internal fun CreatingCategoryItem(
    category: UICategory,
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
