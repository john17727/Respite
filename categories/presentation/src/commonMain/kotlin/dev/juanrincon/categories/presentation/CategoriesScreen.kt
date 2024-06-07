package dev.juanrincon.categories.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.categories.presentation.components.CreatingCategoryItem
import dev.juanrincon.categories.presentation.components.EditingCategoryItem
import dev.juanrincon.categories.presentation.components.SystemCategoryItem
import dev.juanrincon.categories.presentation.components.UserCategoryItem
import dev.juanrincon.categories.presentation.models.CategoryIntent
import dev.juanrincon.categories.presentation.models.CategoryItem
import dev.juanrincon.categories.presentation.models.CategoryState
import dev.juanrincon.core.presentation.animations.fadeInFadeOut
import dev.juanrincon.core.presentation.animations.slideDown
import dev.juanrincon.core.presentation.animations.slideUp
import dev.juanrincon.core.presentation.components.RightActionButton
import dev.juanrincon.core.presentation.components.VerticalBanner
import dev.juanrincon.core.presentation.di.koinViewModel
import dev.juanrincon.core.presentation.navigation.BackHandler
import kotlinx.coroutines.delay

@Composable
fun CategoriesScreenRoot(
    onBackPressed: () -> Unit,
    viewModel: CategoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    CategoriesScreenRootScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                CategoryIntent.NavigateBack -> onBackPressed()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategoriesScreenRootScreen(
    state: CategoryState,
    onIntent: (CategoryIntent) -> Unit,
    showContent: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberLazyListState()
    LaunchedEffect(state.inAddMode) {
        if (state.inAddMode) {
            delay(150)
            scrollState.animateScrollToItem(0)
        }
    }
    Box(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = showContent,
                enter = slideUp { fullHeight -> fullHeight / 12 },
                exit = slideDown { fullHeight -> fullHeight / 12 }
            ) {
                VerticalBanner(
                    text = "Categories",
                    icon = Icons.Rounded.Sell,
                    actionButtonEnabled = state.inEditMode.not() && state.inAddMode.not(),
                    actionButtonIcon = Icons.Rounded.Add,
                    onActionButtonClick = { onIntent(CategoryIntent.CreateItem) },
                    backgroundColor = Color(0xFFA6C994),
                    contentColor = Color(0xFF3C422F),
                )
            }
            AnimatedVisibility(
                visible = showContent
            ) {
                LazyColumn(
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(28.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        end = 24.dp,
                        bottom = 70.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    modifier = Modifier.fillMaxHeight().weight(1f)
                ) {
                    items(
                        state.categories,
                        { item -> item.id },
                        { item -> item::class }) { categoryItem ->
                        AnimatedContent(
                            categoryItem,
                            transitionSpec = ::fadeInFadeOut,
                            modifier = Modifier.animateItemPlacement()
                        ) { item ->
                            when (item) {
                                is CategoryItem.SystemItem -> SystemCategoryItem(
                                    category = item.category,
                                    borderColor = Color(0xFFC2DB9E),
                                    contentColor = Color(0xFF3C422F),
                                )

                                is CategoryItem.UserItem -> UserCategoryItem(
                                    category = item.category,
                                    inEditMode = state.inEditMode || state.inAddMode,
                                    borderColor = Color(0xFFC2DB9E),
                                    contentColor = Color(0xFF3C422F),
                                    onEditClick = { id -> onIntent(CategoryIntent.EditItem(id)) },
                                    onDeleteClick = { id ->
                                        onIntent(
                                            CategoryIntent.DeleteCategory(
                                                id
                                            )
                                        )
                                    },
                                )

                                is CategoryItem.EditingItem -> EditingCategoryItem(
                                    category = item.category,
                                    onSave = { id, name ->
                                        onIntent(
                                            CategoryIntent.UpdateCategory(
                                                id,
                                                name
                                            )
                                        )
                                    },
                                    onCancel = { id -> onIntent(CategoryIntent.CancelEditItem(id)) },
                                    focusRequester = focusRequester,
                                    borderColor = Color(0xFFC2DB9E),
                                    contentColor = Color(0xFF3C422F)
                                )

                                is CategoryItem.CreatingItem -> CreatingCategoryItem(
                                    category = item.category,
                                    onSave = { name -> onIntent(CategoryIntent.CreateCategory(name)) },
                                    onCancel = { onIntent(CategoryIntent.CancelCreateItem) },
                                    focusRequester = focusRequester,
                                    borderColor = Color(0xFFC2DB9E),
                                    contentColor = Color(0xFF3C422F),
                                )
                            }
                        }
                    }
                }
            }
        }

        RightActionButton(
            onClick = { onIntent(CategoryIntent.NavigateBack) },
            backgroundColor = Color(0xFFEDD379),
            contentColor = Color(0xFF684633),
            icon = Icons.AutoMirrored.Rounded.ArrowForward,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
    BackHandler(true) {
        onIntent(CategoryIntent.NavigateBack)
    }
}