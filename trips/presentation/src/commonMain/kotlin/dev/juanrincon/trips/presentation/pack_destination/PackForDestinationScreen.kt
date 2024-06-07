package dev.juanrincon.trips.presentation.pack_destination

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.juanrincon.trips.presentation.models.TripState

@Composable
fun PackForDestinationScreenRoot(
    viewModel: PackForDestinationViewModel
) {
    val state by viewModel.state.collectAsState()
    PackForDestinationScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun PackForDestinationScreen(
    state: TripState,
    onIntent: (PackForDestinationIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
//    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
//        Row(horizontalArrangement = Arrangement.Reverse, modifier = Modifier.fillMaxSize()) {
//            AnimatedVisibility(
//                visible = true, // Change according to navigation and animation libraries
//                enter = slideUp { fullHeight -> fullHeight / 12 },
//                exit = slideDown { fullHeight -> fullHeight / 12 }
//            ) {
//                VerticalBanner(
//                    actionButtonIcon = Icons.Rounded.FlightTakeoff,
//                    onActionButtonClick = { onIntent(PackForDestinationIntent.FinishPacking(state.trip)) },
//                    backgroundColor = Color(0xFFEDD379),
//                    contentColor = Color(0xFF684633),
//                    alignment = BannerAlignment.End
//                )
//            }
//            Column(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
//                LazyColumn(
//                    verticalArrangement = Arrangement.spacedBy(28.dp),
//                    state = listState,
//                    modifier = Modifier.weight(1f)
//                        .padding(top = 16.dp, start = 24.dp, bottom = 16.dp)
//                ) {
//                    items(items, key = { it.id }) { item ->
//                        RightTripItem(
//                            item = item,
//                            onAddClick = onAddClick,
//                            onRemoveClick = onRemoveClick,
//                            borderColor = Color(0xFFFFD55F),
//                            contentColor = Color(0xFF684633),
//                        )
//                    }
//                }
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    LeftActionButton(
//                        onClick = onCancelPacking,
//                        backgroundColor = Color(0xFFA6C994),
//                        contentColor = Color(0xFF3C422F),
//                        icon = Icons.Rounded.Close,
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Icon(
//                            Icons.Rounded.Home,
//                            "",
//                            modifier = Modifier.heightIn(36.dp, 48.dp).widthIn(36.dp, 48.dp)
//                        )
//                        Icon(Icons.Rounded.East, "")
//                        Text(
//                            text = destination,
//                            style = MaterialTheme.typography.displaySmall,
//                        )
//                    }
//                }
//            }
//        }
//    }
}