package dev.juanrincon.luggage.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.animations.fadeInFadeOut
import dev.juanrincon.core.presentation.animations.slideDown
import dev.juanrincon.core.presentation.animations.slideLeft
import dev.juanrincon.core.presentation.animations.slideUp
import dev.juanrincon.core.presentation.components.BannerAlignment
import dev.juanrincon.core.presentation.components.LeftActionButton
import dev.juanrincon.core.presentation.components.VerticalBanner
import dev.juanrincon.core.presentation.di.koinViewModel
import dev.juanrincon.core.presentation.navigation.BackHandler
import dev.juanrincon.core.presentation.utils.Reverse
import dev.juanrincon.luggage.presentation.components.CreatingLuggageItem
import dev.juanrincon.luggage.presentation.components.EditingLuggageItem
import dev.juanrincon.luggage.presentation.components.UserLuggageItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LuggageScreenRoot(
    onBackPressed: () -> Unit,
    viewModel: LuggageViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    LuggageScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                LuggageIntent.NavigateBack -> onBackPressed()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LuggageScreen(
    state: LuggageState,
    onIntent: (LuggageIntent) -> Unit,
    showContent: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    LaunchedEffect(state.inAddMode) {
        if (state.inAddMode) {
            delay(150)
            scrollState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Reverse, modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = showContent,
                enter = slideUp { fullHeight -> fullHeight / 12 },
                exit = slideDown { fullHeight -> fullHeight / 12 }
            ) {
                VerticalBanner(
                    text = "Luggage",
                    icon = Icons.Rounded.Luggage,
                    actionButtonIcon = Icons.Rounded.Add,
                    onActionButtonClick = { onIntent(LuggageIntent.CreateItem) },
                    actionButtonEnabled = state.inEditMode.not() && state.inAddMode.not(),
                    backgroundColor = Color(0xFFEDD379),
                    contentColor = Color(0xFF684633),
                    alignment = BannerAlignment.End
                )
            }
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideLeft { fullWidth -> fullWidth / 12 }
            ) {
                LazyColumn(
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(28.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 24.dp,
                        bottom = 70.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    itemsIndexed(
                        state.luggage,
                        { i, item -> item.id },
                        { i, item -> item::class }) { index, luggageItem ->
                        AnimatedContent(
                            luggageItem,
                            transitionSpec = ::fadeInFadeOut,
                            modifier = Modifier.animateItemPlacement()
                        ) { item ->
                            when (item) {
                                is LuggageItem.CreatingItem -> {
                                    CreatingLuggageItem(
                                        categories = state.categories,
                                        onCancel = { onIntent(LuggageIntent.CancelCreateItem) },
                                        onSave = { name, category ->
                                            onIntent(
                                                LuggageIntent.CreateLuggage(
                                                    name,
                                                    category
                                                )
                                            )
                                        },
                                        focusRequester = focusRequester,
                                        borderColor = Color(0xFFFFD55F),
                                        contentColor = Color(0xFF684633),
                                    )
                                }

                                is LuggageItem.EditingItem -> EditingLuggageItem(
                                    item = item.item,
                                    categories = state.categories,
                                    onCancel = { id -> onIntent(LuggageIntent.CancelEditItem(id)) },
                                    onSave = { id, name, category ->
                                        onIntent(
                                            LuggageIntent.UpdateLuggage(
                                                id,
                                                name,
                                                category
                                            )
                                        )
                                    },
                                    onExpanded = { delay ->
                                        coroutineScope.launch {
                                            delay(delay)
                                            scrollState.animateScrollToItem(index)
                                        }
                                    },
                                    focusRequester = focusRequester,
                                    borderColor = Color(0xFFFFD55F),
                                    contentColor = Color(0xFF684633),
                                )

                                is LuggageItem.UserItem -> UserLuggageItem(
                                    item = item.item,
                                    inEditMode = state.inEditMode || state.inAddMode,
                                    borderColor = Color(0xFFFFD55F),
                                    contentColor = Color(0xFF684633),
                                    onEditClick = { id -> onIntent(LuggageIntent.EditItem(id)) },
                                    onDeleteClick = { id -> onIntent(LuggageIntent.DeleteLuggage(id)) }
                                )

                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
        LeftActionButton(
            onClick = { onIntent(LuggageIntent.NavigateBack) },
            backgroundColor = Color(0xFFA6C994),
            contentColor = Color(0xFF3C422F),
            icon = Icons.AutoMirrored.Rounded.ArrowBack,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
    BackHandler(true) {
        onIntent(LuggageIntent.NavigateBack)
    }
}