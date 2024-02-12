package dev.juanrincon.respite.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun LeftActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
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
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        modifier = modifier
    ) {
        Icon(icon, null)
    }
}

@Composable
fun RightActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
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
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        modifier = modifier
    ) {
        Icon(icon, null)
    }
}
