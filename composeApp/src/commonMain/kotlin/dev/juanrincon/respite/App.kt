package dev.juanrincon.respite

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import dev.juanrincon.respite.navigation.TripScreen
import dev.juanrincon.respite.presentation.theme.RespiteTheme

@Composable
fun App() {
    RespiteTheme {
        Surface {
            Navigator(TripScreen())
        }
    }
}