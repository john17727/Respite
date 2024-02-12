package dev.juanrincon.respite.presentation.categories

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.navigation.BackHandler
import dev.juanrincon.respite.presentation.animations.fadeInFadeOut
import dev.juanrincon.respite.presentation.animations.slideDown
import dev.juanrincon.respite.presentation.animations.slideUp
import dev.juanrincon.respite.presentation.components.CreatingCategoryItem
import dev.juanrincon.respite.presentation.components.EditingCategoryItem
import dev.juanrincon.respite.presentation.components.RightActionButton
import dev.juanrincon.respite.presentation.components.SystemCategoryItem
import dev.juanrincon.respite.presentation.components.UserCategoryItem
import dev.juanrincon.respite.presentation.components.VerticalBanner

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesUI(
    categories: List<CategoryItem>,
    inEditMode: Boolean,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onEditSave: (Int, String) -> Unit,
    onCreateClick: () -> Unit,
    onCreateSave: (String) -> Unit,
    onCreateCancel: () -> Unit,
    onEditCancel: (Int) -> Unit,
    onBackClick: () -> Unit,
    showContent: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
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
                    backgroundColor = Color(0xFFA6C994),
                    contentColor = Color(0xFF3C422F),
                )
            }
            AnimatedVisibility(
                visible = showContent
            ) {
                LazyColumn(
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
                        categories,
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
                                    inEditMode = inEditMode,
                                    borderColor = Color(0xFFC2DB9E),
                                    contentColor = Color(0xFF3C422F),
                                    onEditClick = onEditClick,
                                    onDeleteClick = onDeleteClick,
                                )

                                is CategoryItem.EditingItem -> EditingCategoryItem(
                                    category = item.category,
                                    onSave = onEditSave,
                                    onCancel = onEditCancel,
                                    focusRequester = focusRequester,
                                    borderColor = Color(0xFFC2DB9E),
                                    contentColor = Color(0xFF3C422F)
                                )

                                is CategoryItem.CreatingItem -> CreatingCategoryItem(
                                    category = item.category,
                                    onSave = onCreateSave,
                                    onCancel = onCreateCancel,
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

        AnimatedVisibility(
            visible = inEditMode.not(),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            RightActionButton(
                onClick = onCreateClick,
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                icon = Icons.Rounded.Add,
            )
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart).padding(8.dp),
        ) {
            Icon(Icons.Rounded.ArrowBack, null)
        }
    }
    BackHandler(true) {
        onBackClick()
    }
}
