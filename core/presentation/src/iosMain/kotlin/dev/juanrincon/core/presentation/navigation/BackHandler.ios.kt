package dev.juanrincon.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

//import dev.juanrincon.respite.store

@Composable
actual fun BackHandler(isEnabled: Boolean, onBack: () -> Unit) {
    LaunchedEffect(isEnabled) {
        // TODO: Fix later
//        store.events.collect {
//            if (isEnabled) {
//                onBack()
//            }
//        }
    }
}