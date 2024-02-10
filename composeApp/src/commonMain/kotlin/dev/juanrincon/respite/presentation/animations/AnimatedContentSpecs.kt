package dev.juanrincon.respite.presentation.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

fun <S> fadeInFadeOut(transitionScope: AnimatedContentTransitionScope<S>): ContentTransform =
    fadeIn(
        animationSpec = tween(256, delayMillis = 90)
    ) togetherWith fadeOut(animationSpec = tween(256, delayMillis = 90))
