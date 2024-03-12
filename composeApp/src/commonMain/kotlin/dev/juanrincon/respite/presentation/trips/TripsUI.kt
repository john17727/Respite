package dev.juanrincon.respite.presentation.trips

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FlightTakeoff
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.TripItem
import dev.juanrincon.respite.domain.model.TripStatus
import dev.juanrincon.respite.presentation.components.CallToActionTag
import dev.juanrincon.respite.presentation.components.EditingTripItem
import dev.juanrincon.respite.presentation.components.InputTag
import dev.juanrincon.respite.presentation.components.LeftActionButton
import dev.juanrincon.respite.presentation.components.RightActionButton
import dev.juanrincon.respite.presentation.extensions.normalizedItemPosition
import dev.juanrincon.respite.presentation.theme.RespiteTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.KProperty

@Composable
fun TripsUI(
    state: TripState,
    onCreateNewTrip: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit,
    onAddItemCountClick: (tripId: Int, item: TripItem) -> Unit,
    onRemoveItemCountClick: (tripId: Int, item: TripItem) -> Unit,
    onAddNewItemClick: () -> Unit,
    onFinishPackingClick: () -> Unit,
) {
    if (state.trip != null) {
        when (state.trip.status) {
            TripStatus.Destination -> TODO()
            TripStatus.PackingDestination -> PackForDestinationPage(
                destination = state.trip.name.uppercase(),
                items = state.trip.items,
                onAddClick = { item -> onAddItemCountClick(state.trip.id, item) },
                onRemoveClick = { item -> onRemoveItemCountClick(state.trip.id, item) },
                onAddNewItemClick = onAddNewItemClick,
                onFinishPackingClick = onFinishPackingClick
            )

            TripStatus.PackingNextDestination -> TODO()
        }
    } else {
        EmptyPage(
            onSaveNewTrip = onCreateNewTrip,
            onCategoriesClick = onCategoriesClick,
            onLuggageClick = onLuggageClick
        )
    }
}

@Composable
fun EmptyPage(
    onSaveNewTrip: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit
) {
    var showCreationTag by remember { mutableStateOf(false) }
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 36.dp)
        ) {
            AnimatedContent(
                targetState = showCreationTag,
                modifier = Modifier.fillMaxWidth().heightIn(min = 400.dp, max = 600.dp),
            ) { newTripView ->
                if (newTripView) {
                    InputTag(
                        onSaveInputClick = onSaveNewTrip,
                        backgroundColor = Color(0xFF2E6C82),
                    )
                } else {
                    CallToActionTag(
                        callToActionText = "Adventure Awaits!",
                        onCallToActionClick = { showCreationTag = true },
                        backgroundColor = Color(0xFF2E6C82),
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            LeftActionButton(
                onClick = onCategoriesClick,
                backgroundColor = Color(0xFFA6C994),
                contentColor = Color(0xFF3C422F),
                icon = Icons.Rounded.Sell,
            )
            RightActionButton(
                onClick = onLuggageClick,
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                icon = Icons.Rounded.Luggage,
            )
        }
    }
}

@Composable
private fun PackForDestinationPage(
    destination: String,
    items: List<TripItem>,
    onAddClick: (item: TripItem) -> Unit,
    onRemoveClick: (item: TripItem) -> Unit,
    onAddNewItemClick: () -> Unit,
    onFinishPackingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    Column(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState,
            modifier = Modifier.weight(1f).padding(bottom = 16.dp, start = 16.dp, end = 16.dp).clip(shape = MaterialTheme.shapes.small)

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
                onClick = onAddNewItemClick,
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                icon = Icons.Rounded.Add,
            )
            Text(
                text = destination,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(
                    bottom = 8.dp + WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding()
                )
            )
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
            onAddNewItemClick = {},
            onFinishPackingClick = {}
        )
    }
}

@Composable
@Preview
fun PreviewEmptyPage() {
    RespiteTheme {
        EmptyPage(
            onSaveNewTrip = {},
            onCategoriesClick = {},
            onLuggageClick = {}
        )
    }
}