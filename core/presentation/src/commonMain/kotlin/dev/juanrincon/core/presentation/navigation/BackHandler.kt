package dev.juanrincon.core.presentation.navigation

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(isEnabled: Boolean, onBack: () -> Unit)