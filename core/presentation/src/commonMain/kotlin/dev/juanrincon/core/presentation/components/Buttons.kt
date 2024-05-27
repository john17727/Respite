package dev.juanrincon.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.extensions.startBorder

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

@Composable
fun ActionButtons(
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    leftButtonIcon: ImageVector,
    rightButtonIcon: ImageVector,
    rightButtonEnabled: Boolean = true,
    leftButtonEnabled: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = onLeftButtonClick,
            modifier = Modifier.startBorder(borderColor, 2.dp, 6.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor),
            enabled = leftButtonEnabled
        ) {
            Icon(leftButtonIcon, null)
        }
        IconButton(
            onClick = onRightButtonClick,
            modifier = Modifier.startBorder(borderColor, 2.dp, 6.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor),
            enabled = rightButtonEnabled
        ) {
            Icon(rightButtonIcon, null)
        }
    }
}
