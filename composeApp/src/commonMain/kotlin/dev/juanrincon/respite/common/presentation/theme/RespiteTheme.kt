package dev.juanrincon.respite.common.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorTheme = lightColorScheme(
    surface = theme_light_surface
)

@Composable
fun RespiteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!darkTheme) {
        lightColorTheme
    } else {
        lightColorTheme // TODO: Change to dark theme when ready
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}