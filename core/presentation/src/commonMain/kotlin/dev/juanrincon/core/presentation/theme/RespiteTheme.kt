package dev.juanrincon.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import respite.core.presentation.generated.resources.Molot
import respite.core.presentation.generated.resources.Oswald_Regular
import respite.core.presentation.generated.resources.Res

private val lightColorTheme = lightColorScheme(
    surface = theme_light_surface,
    background = theme_light_background
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

    val respiteTypography = Typography(
        displayLarge = MaterialTheme.typography.displayLarge.copy(
            fontFamily = FontFamily(Font(Res.font.Molot))
        ),
        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = FontFamily(Font(Res.font.Molot))
        ),
        displaySmall = MaterialTheme.typography.displaySmall.copy(
            fontFamily = FontFamily(Font(Res.font.Oswald_Regular))
        ),
        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = FontFamily(Font(Res.font.Oswald_Regular))
        ),
        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = FontFamily(Font(Res.font.Oswald_Regular))
        )
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = respiteTypography,
        content = content
    )
}