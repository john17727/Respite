package dev.juanrincon.trips.presentation.empty_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.components.CallToActionTag
import dev.juanrincon.core.presentation.components.InputTag
import dev.juanrincon.core.presentation.components.LeftActionButton
import dev.juanrincon.core.presentation.components.RightActionButton
import dev.juanrincon.core.presentation.utils.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun EmptyScreenRoot(
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit,
    onNavigateToPackForDestination: () -> Unit,
    onNavigateToDestination: () -> Unit,
    onNavigateToNextDestination: () -> Unit,
    viewModel: EmptyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onIntent(EmptyScreenIntent.GetTrips)
    }
    ObserveAsEvents(viewModel.sideEffect) { event ->
        when (event) {
            EmptyScreenEvent.TripCreationSuccess -> onNavigateToPackForDestination()
            EmptyScreenEvent.Destination -> onNavigateToDestination()
            EmptyScreenEvent.PackForDestination -> onNavigateToPackForDestination()
            EmptyScreenEvent.PackForNextDestination -> onNavigateToNextDestination()
        }
    }
    EmptyScreenRootScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                EmptyScreenIntent.NavigateToCategories -> onCategoriesClick()
                EmptyScreenIntent.NavigateToLuggage -> onLuggageClick()
                EmptyScreenIntent.NavigateToPackForDestination -> onNavigateToPackForDestination()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@Composable
private fun EmptyScreenRootScreen(
    state: EmptyScreenState,
    onIntent: (EmptyScreenIntent) -> Unit
) {
    var showCreationTag by remember { mutableStateOf(false) }
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 36.dp)
        ) {
            if (!state.loading) {
                AnimatedContent(
                    targetState = showCreationTag,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 400.dp, max = 600.dp),
                ) { newTripView ->
                    if (newTripView) {
                        InputTag(
                            onSaveInputClick = { tripName ->
                                onIntent(
                                    EmptyScreenIntent.CreateTrip(
                                        tripName
                                    )
                                )
                            },
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
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            LeftActionButton(
                onClick = { onIntent(EmptyScreenIntent.NavigateToCategories) },
                backgroundColor = Color(0xFFA6C994),
                contentColor = Color(0xFF3C422F),
                icon = Icons.Rounded.Sell,
            )
            RightActionButton(
                onClick = { onIntent(EmptyScreenIntent.NavigateToLuggage) },
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                icon = Icons.Rounded.Luggage,
            )
        }
    }
}