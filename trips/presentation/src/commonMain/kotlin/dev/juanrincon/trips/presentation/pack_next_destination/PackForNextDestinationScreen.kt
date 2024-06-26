package dev.juanrincon.trips.presentation.pack_next_destination

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FlightLand
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.animations.slideDown
import dev.juanrincon.core.presentation.animations.slideLeft
import dev.juanrincon.core.presentation.animations.slideUp
import dev.juanrincon.core.presentation.components.BannerAlignment
import dev.juanrincon.core.presentation.components.LeftActionButton
import dev.juanrincon.core.presentation.components.VerticalBanner
import dev.juanrincon.core.presentation.utils.ObserveAsEvents
import dev.juanrincon.core.presentation.utils.Reverse
import dev.juanrincon.trips.presentation.components.PackItem
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.ParametersHolder

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PackForeNextDestinationScreenRoot(
    onNavigateBack: () -> Unit,
    parametersHolder: ParametersHolder,
    viewModel: PackForNextDestinationViewModel = koinViewModel(parameters = { parametersHolder })
) {
    ObserveAsEvents(viewModel.sideEffect) { event ->
        when (event) {
            PackForNextDestinationEvent.CancelPacking -> onNavigateBack()
            PackForNextDestinationEvent.FinishTrip -> onNavigateBack()
        }
    }
    val state by viewModel.state.collectAsState()
    PackForeNextDestinationScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun PackForeNextDestinationScreen(
    state: PackForNextDestinationState,
    onIntent: (PackForNextDestinationIntent) -> Unit
) {
    val listState = rememberLazyListState()
    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Reverse, modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = state.transitionAnimation,
                enter = slideUp { fullHeight -> fullHeight / 12 },
                exit = slideDown { fullHeight -> fullHeight / 12 }
            ) {
                VerticalBanner(
                    text = state.trip.name,
                    icon = Icons.Rounded.FlightLand,
                    actionButtonIcon = Icons.Rounded.Check,
                    onActionButtonClick = {
                        onIntent(
                            PackForNextDestinationIntent.FinishPacking(
                                state.trip.id
                            )
                        )
                    },
                    actionButtonEnabled = state.isNextButtonEnabled,
                    backgroundColor = Color(0xFFEDD379),
                    contentColor = Color(0xFF684633),
                    alignment = BannerAlignment.End
                )
            }
            AnimatedVisibility(
                visible = state.listAnimation,
                enter = fadeIn() + slideLeft { fullWidth -> fullWidth / 12 },
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(28.dp),
                    state = listState,
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 24.dp,
                        bottom = 70.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.trip.items, key = { it.id }) { item ->
                        PackItem(
                            item = item,
                            onAddClick = {
                                onIntent(
                                    PackForNextDestinationIntent.PackItem(
                                        state.trip.id,
                                        item
                                    )
                                )
                            },
                            onRemoveClick = {
                                onIntent(
                                    PackForNextDestinationIntent.UnpackItem(
                                        state.trip.id,
                                        item
                                    )
                                )
                            },
                            borderColor = Color(0xFFFFD55F),
                            contentColor = Color(0xFF684633),
                        )
                    }
                }
            }
        }
        LeftActionButton(
            onClick = { onIntent(PackForNextDestinationIntent.CancelPacking(state.trip.id)) },
            backgroundColor = Color(0xFFA6C994),
            contentColor = Color(0xFF3C422F),
            icon = Icons.Rounded.Close,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}