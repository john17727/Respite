package dev.juanrincon.core.presentation.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.ui.unit.IntOffset

fun <S> fadeInFadeOut(transitionScope: AnimatedContentTransitionScope<S>): ContentTransform =
    fadeIn(
        animationSpec = tween(256, delayMillis = 90)
    ) togetherWith fadeOut(animationSpec = tween(256, delayMillis = 90))

fun slideUp(initialOffset: (fullHeight: Int) -> Int): EnterTransition = slideIn { fullSize ->
    IntOffset(0, fullSize.height - initialOffset(fullSize.height))
}

fun slideDown(initialOffset: (fullHeight: Int) -> Int): ExitTransition = slideOut { fullSize ->
    IntOffset(0, fullSize.height - initialOffset(fullSize.height))
}

fun slideLeft(initialOffset: (fullWidth: Int) -> Int): EnterTransition = slideIn { fullSize ->
    IntOffset(fullSize.width - initialOffset(fullSize.width), 0)
}