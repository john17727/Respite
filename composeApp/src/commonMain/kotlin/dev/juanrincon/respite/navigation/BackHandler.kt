package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(isEnabled: Boolean, onBack: () -> Unit)