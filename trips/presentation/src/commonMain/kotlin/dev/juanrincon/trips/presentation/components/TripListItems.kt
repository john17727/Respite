package dev.juanrincon.trips.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.extensions.startBorder
import dev.juanrincon.core.presentation.extensions.topBorder
import dev.juanrincon.trips.domain.TripItem

@Composable
fun RightTripItem(
    item: TripItem,
    onAddClick: (TripItem) -> Unit,
    onRemoveClick: (TripItem) -> Unit,
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
            CountActionButtons(
                count = item.amount,
                onLeftButtonClick = { onRemoveClick(item) },
                onRightButtonClick = { onAddClick(item) },
                leftButtonIcon = Icons.Rounded.Remove,
                rightButtonIcon = Icons.Rounded.Add,
                borderColor = borderColor,
                contentColor = contentColor
            )
        }
        Text(
            text = item.category.uppercase(),
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
fun CountActionButtons(
    count: Int,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    leftButtonIcon: ImageVector,
    rightButtonIcon: ImageVector,
    buttonsEnabled: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
    ) {
        IconButton(
            onClick = onLeftButtonClick,
            modifier = Modifier.startBorder(borderColor, 2.dp, 6.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor),
            enabled = buttonsEnabled
        ) {
            Icon(leftButtonIcon, null)
        }
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = contentColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxHeight().fillMaxWidth(.33f)
                .startBorder(borderColor, 2.dp, 6.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        IconButton(
            onClick = onRightButtonClick,
            modifier = Modifier.startBorder(borderColor, 2.dp, 6.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor),
            enabled = buttonsEnabled
        ) {
            Icon(rightButtonIcon, null)
        }
    }
}
