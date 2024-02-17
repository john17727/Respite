package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LeftActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    navBarPadding: Dp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = CutCornerShape(topEnd = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(
            start = 24.dp,
            top = 16.dp,
            end = 24.dp,
            bottom = 16.dp + navBarPadding
        ),
        modifier = modifier
    ) {
        Icon(icon, null)
    }
}

@Composable
fun RightActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    navBarPadding: Dp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    contentColor: Color = MaterialTheme.colorScheme.onTertiary,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = CutCornerShape(topStart = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(
            start = 24.dp,
            top = 16.dp,
            end = 24.dp,
            bottom = 16.dp + navBarPadding
        ),
        modifier = modifier
    ) {
        Icon(icon, null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    // Removes gaps around buttons
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            shape = RoundedCornerShape(0.dp),
            modifier = modifier
        ) {
            Text(text = text.uppercase(), style = MaterialTheme.typography.labelLarge)
        }
    }
}
