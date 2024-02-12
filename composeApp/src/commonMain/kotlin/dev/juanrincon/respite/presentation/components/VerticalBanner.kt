package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalBanner(
    text: String,
    icon: ImageVector,
    navBarPadding: Dp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
    iconDescription: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    alignment: BannerAlignment = BannerAlignment.Start,
    modifier: Modifier = Modifier
) {
    val shape = when (alignment) {
        BannerAlignment.End -> CutCornerShape(topStart = 16.dp)
        BannerAlignment.Start -> CutCornerShape(topEnd = 16.dp)
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxHeight()
            .background(backgroundColor, shape)
            .padding(start = 10.dp, top = 16.dp, end = 10.dp, bottom = 16.dp + navBarPadding),
    ) {
        VerticalText(
            text = text.uppercase(),
            degrees = alignment.textRotation,
            style = MaterialTheme.typography.displaySmall,
            color = contentColor
        )
        Spacer(modifier = Modifier.height(32.dp))
        Icon(icon, iconDescription, tint = contentColor)
    }
}

sealed class BannerAlignment(val textRotation: Float) {
    data object Start : BannerAlignment(-90f)
    data object End : BannerAlignment(90f)
}