package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.presentation.extensions.startBorder
import dev.juanrincon.respite.presentation.extensions.topBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripCard(trip: Trip, onClick: (Int) -> Unit) {
    Card(onClick = { onClick(trip.id) }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = trip.name)
            Text(text = trip.totalItems.toString())
        }
    }
}

@Composable
fun SystemCategoryItem(
    category: Category,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onInfoClick: (String) -> Unit,
) {
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
                onClick = { onInfoClick(category.description) },
                colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
            ) {
                Icon(Icons.Rounded.Info, null)
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
) {
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
            modifier = Modifier.padding(top = 6.dp, start = 4.dp).fillMaxWidth(0.50f)
        )
        ActionButtons(
            onEditClick = { onEditClick(category.id) },
            onDeleteClick = { onDeleteClick(category.id) },
            borderColor = borderColor,
            contentColor = contentColor
        )
    }
}

@Composable
fun EditingCategoryItem(
    category: Category,
    onNameUpdate: (Int, String) -> Unit,
    onSave: (Category) -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
    ) {
        RespiteTextField(
            value = category.name,
            onValueChange = { onNameUpdate(category.id, it) },
            modifier = Modifier.padding(top = 6.dp, start = 4.dp),
            textStyle = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = { onSave(category) },
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
        ) {
            Icon(Icons.Rounded.Done, null)
        }
    }
}

@Composable
fun CreatingCategoryItem(
    category: Category,
    onNameUpdate: (Int, String) -> Unit,
    onSave: (Category) -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().topBorder(borderColor, 2.dp)
    ) {
        RespiteTextField(
            value = category.name,
            onValueChange = { onNameUpdate(category.id, it) },
            modifier = Modifier.padding(top = 6.dp, start = 4.dp),
            textStyle = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = { onSave(category) },
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
        ) {
            Icon(Icons.Rounded.Done, null)
        }
    }
}

@Composable
fun ActionButtons(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = onEditClick,
            modifier = Modifier.startBorder(borderColor, 2.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
        ) {
            Icon(Icons.Rounded.Edit, null)
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.startBorder(borderColor, 2.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor)
        ) {
            Icon(Icons.Rounded.Delete, null)
        }
    }
}