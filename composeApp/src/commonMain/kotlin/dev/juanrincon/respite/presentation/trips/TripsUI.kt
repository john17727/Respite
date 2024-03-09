package dev.juanrincon.respite.presentation.trips

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.TripItem
import dev.juanrincon.respite.domain.model.TripStatus
import dev.juanrincon.respite.presentation.components.CallToActionTag
import dev.juanrincon.respite.presentation.components.EditingTripItem
import dev.juanrincon.respite.presentation.components.InputTag
import dev.juanrincon.respite.presentation.components.LeftActionButton
import dev.juanrincon.respite.presentation.components.RightActionButton

@Composable
fun TripsUI(
    state: TripState,
    onToggleCreateNewTrip: () -> Unit,
    onCreateNewTrip: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit
) {
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        if (state.trip != null) {
            TripItemList(
                items = state.trip.items,
                status = state.trip.status,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 36.dp)
            ) {
                AnimatedContent(
                    targetState = state.createNewTrip,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 400.dp, max = 600.dp),
                ) { newTripView ->
                    if (newTripView) {
                        InputTag(
                            onSaveInputClick = onCreateNewTrip,
                            backgroundColor = Color(0xFF2E6C82),
                        )
                    } else {
                        CallToActionTag(
                            callToActionText = "Adventure Awaits!",
                            onCallToActionClick = onToggleCreateNewTrip,
                            backgroundColor = Color(0xFF2E6C82),
                        )
                    }
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
            AnimatedVisibility(visible = state.trip != null) {
                Text(
                    text = state.trip!!.name.uppercase(),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(
                        bottom = 8.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    )
                )
            }
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
private fun TripItemList(
    items: List<TripItem>,
    status: TripStatus,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(items, key = { it.id }, contentType = { status::class }) { item ->
            when (status) {
                is TripStatus.PackingDestination -> EditingTripItem(item = item)
                TripStatus.Destination -> TODO()
                TripStatus.PackingNextDestination -> TODO()
            }
        }
    }
}