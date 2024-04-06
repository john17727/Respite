package dev.juanrincon.respite.trips.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.East
import androidx.compose.material.icons.rounded.FlightTakeoff
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.common.presentation.components.EditingTripItem
import dev.juanrincon.respite.common.presentation.components.LeftActionButton
import dev.juanrincon.respite.common.presentation.components.RightActionButton
import dev.juanrincon.respite.common.presentation.extensions.normalizedItemPosition
import dev.juanrincon.respite.common.presentation.theme.RespiteTheme
import dev.juanrincon.respite.trips.domain.TripItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PackForDestinationPage(
    destination: String,
    items: List<TripItem>,
    onAddClick: (item: TripItem) -> Unit,
    onRemoveClick: (item: TripItem) -> Unit,
    onCancelPacking: () -> Unit,
    onFinishPackingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    Column(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState,
            modifier = Modifier.weight(1f).padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                .clip(shape = MaterialTheme.shapes.small)

        ) {
            items(items, key = { it.id }) { item ->
                EditingTripItem(
                    item = item,
                    onAddClick = onAddClick,
                    onRemoveClick = onRemoveClick,
                    modifier = Modifier.alpha(1 - listState.layoutInfo.normalizedItemPosition(item.id))
                )
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            LeftActionButton(
                onClick = onCancelPacking,
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                icon = Icons.Rounded.Close,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Rounded.Home,
                    "",
                    modifier = Modifier.heightIn(36.dp, 48.dp).widthIn(36.dp, 48.dp)
                )
                Icon(Icons.Rounded.East, "")
                Text(
                    text = destination,
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            RightActionButton(
                onClick = onFinishPackingClick,
                backgroundColor = Color(0xFFA6C994),
                contentColor = Color(0xFF3C422F),
                icon = Icons.Rounded.FlightTakeoff,
            )
        }
    }
}

@Composable
@Preview
fun PreviewPackForDestinationPage() {
    RespiteTheme {
        PackForDestinationPage(
            destination = "Trip Name",
            items = listOf(
                TripItem(0, "Item 1", "Category 1", 0, 0),
                TripItem(1, "Item 2", "Category 1", 0, 0),
                TripItem(2, "Item 3", "Category 1", 0, 0),
                TripItem(3, "Item 4", "Category 1", 0, 0),
                TripItem(4, "Item 5", "Category 1", 0, 0),
                TripItem(5, "Item 6", "Category 1", 0, 0),
                TripItem(6, "Item 7", "Category 1", 0, 0),
                TripItem(7, "Item 8", "Category 1", 0, 0),
            ),
            onAddClick = {},
            onRemoveClick = {},
            onCancelPacking = {},
            onFinishPackingClick = {}
        )
    }
}
