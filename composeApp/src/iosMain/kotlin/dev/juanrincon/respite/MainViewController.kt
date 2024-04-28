package dev.juanrincon.respite

import androidx.compose.ui.window.ComposeUIViewController
import dev.juanrincon.respite.navigation.Action

fun MainViewController() = ComposeUIViewController { App() }

fun onBackGesture() {
    store.send(Action.OnBackPressed)
}
