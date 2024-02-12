package dev.juanrincon.respite.presentation.luggage

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.presentation.animations.fadeInFadeOut
import dev.juanrincon.respite.presentation.animations.slideDown
import dev.juanrincon.respite.presentation.animations.slideLeft
import dev.juanrincon.respite.presentation.animations.slideUp
import dev.juanrincon.respite.presentation.components.BannerAlignment
import dev.juanrincon.respite.presentation.components.CreatingLuggageItem
import dev.juanrincon.respite.presentation.components.EditingLuggageItem
import dev.juanrincon.respite.presentation.components.LeftActionButton
import dev.juanrincon.respite.presentation.components.UserLuggageItem
import dev.juanrincon.respite.presentation.components.VerticalBanner
import dev.juanrincon.respite.presentation.extensions.Reverse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LuggageUI(
    luggage: List<LuggageItem>,
    categories: List<Category>,
    inEditMode: Boolean,
    onDeleteClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onEditSave: (Int, String, Int) -> Unit,
    onEditCancel: (Int) -> Unit,
    onCreateClick: () -> Unit,
    onCreateSave: (String, Int) -> Unit,
    onCreateCancel: () -> Unit,
    onBackClick: () -> Unit,
    showContent: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    Box(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Reverse, modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = showContent,
                enter = slideUp { fullHeight -> fullHeight / 12 },
                exit = slideDown { fullHeight -> fullHeight / 12 }
            ) {
                VerticalBanner(
                    text = "Luggage",
                    icon = Icons.Rounded.Luggage,
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
                    contentPadding = PaddingValues(top = 16.dp, start = 24.dp, bottom = 70.dp),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    itemsIndexed(
                        luggage,
                        { i, item -> item.id },
                        { i, item -> item::class }) { index, luggageItem ->
                    AnimatedContent(
                        luggageItem,
                        transitionSpec = ::fadeInFadeOut,
                        modifier = Modifier.animateItemPlacement()
                    ) { item ->
                        when (item) {
                            is LuggageItem.CreatingItem -> CreatingLuggageItem(
                                categories = categories,
                                onCancel = onCreateCancel,
                                onSave = onCreateSave,
                                focusRequester = focusRequester,
                                borderColor = Color(0xFFFFD55F),
                                contentColor = Color(0xFF684633),
                            )

                            is LuggageItem.EditingItem -> EditingLuggageItem(
                                item = item.item,
                                categories = categories,
                                onCancel = onEditCancel,
                                onSave = onEditSave,
                                onExpanded = {
                                    coroutineScope.launch {
                                        delay(200)
                                        scrollState.animateScrollToItem(index)
                                    }
                                },
                                focusRequester = focusRequester,
                                borderColor = Color(0xFFFFD55F),
                                contentColor = Color(0xFF684633),
                            )

                            is LuggageItem.UserItem -> UserLuggageItem(
                                item = item.item,
                                inEditMode = inEditMode,
                                borderColor = Color(0xFFFFD55F),
                                contentColor = Color(0xFF684633),
                                onEditClick = onEditClick,
                                onDeleteClick = onDeleteClick
                            )
                        }
                    }
                }
            }
            }
        }
        AnimatedVisibility(
            inEditMode.not(),
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            LeftActionButton(
                onClick = onCreateClick,
                backgroundColor = Color(0xFFA6C994),
                contentColor = Color(0xFF3C422F),
                icon = Icons.Rounded.Add
            )
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
        ) {
            Icon(Icons.Rounded.ArrowBack, null)
        }
    }
}