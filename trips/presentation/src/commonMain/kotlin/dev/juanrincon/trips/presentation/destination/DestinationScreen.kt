package dev.juanrincon.trips.presentation.destination

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.PinDrop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.animations.slideDown
import dev.juanrincon.core.presentation.animations.slideUp
import dev.juanrincon.core.presentation.components.RightActionButton
import dev.juanrincon.core.presentation.components.VerticalBanner
import dev.juanrincon.core.presentation.navigation.BackHandler
import dev.juanrincon.core.presentation.utils.ObserveAsEvents
import dev.juanrincon.trips.presentation.components.LeftTripItem
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.ParametersHolder

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DestinationScreenRoot(
    onNavigateBack: () -> Unit,
    parametersHolder: ParametersHolder,
    viewModel: DestinationViewModel = koinViewModel(parameters = { parametersHolder })
) {
    ObserveAsEvents(viewModel.sideEffect) { event ->
        when (event) {
            DestinationEvent.CancelTrip -> onNavigateBack()
        }
    }
    val state by viewModel.state.collectAsState()

    DestinationScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun DestinationScreen(
    state: DestinationState,
    onIntent: (DestinationIntent) -> Unit
) {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = state.transitionAnimation,
                enter = slideUp { fullHeight -> fullHeight / 12 },
                exit = slideDown { fullHeight -> fullHeight / 12 }
            ) {
                VerticalBanner(
                    text = state.trip.name,
                    icon = Icons.Rounded.PinDrop,
                    actionButtonIcon = Icons.Rounded.Close,
                    onActionButtonClick = { onIntent(DestinationIntent.CancelTrip(state.trip.id)) },
                    backgroundColor = Color(0xFFA6C994),
                    contentColor = Color(0xFF3C422F),
                )
            }
            AnimatedVisibility(
                visible = state.listAnimation
            ) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(28.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        end = 24.dp,
                        bottom = 70.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    modifier = Modifier.fillMaxHeight().weight(1f)
                ) {
                    items(state.trip.items, key = { it.id }) { item ->
                        LeftTripItem(
                            item = item,
                            borderColor = Color(0xFFC2DB9E),
                            contentColor = Color(0xFF3C422F)
                        )
                    }
                }
            }
        }

        RightActionButton(
            onClick = {},
            backgroundColor = Color(0xFFEDD379),
            contentColor = Color(0xFF684633),
            icon = Icons.Rounded.Luggage,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
    BackHandler(true) {
        onIntent(DestinationIntent.CancelTrip(state.trip.id))
    }
}