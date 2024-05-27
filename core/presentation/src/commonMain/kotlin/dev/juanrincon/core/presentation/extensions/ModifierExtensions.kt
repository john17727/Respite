package dev.juanrincon.core.presentation.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }

fun Modifier.topBorder(color: Color, strokeWidth: Dp = 1.dp) = this.drawBehind {
    val borderSize = strokeWidth.toPx()
    val y = 0f + (borderSize / 2)
    drawLine(
        color = color,
        start = Offset(0f, y),
        end = Offset(size.width, y),
        strokeWidth = borderSize
    )
}

fun Modifier.startBorder(color: Color, strokeWidth: Dp = 1.dp, bottomOffset: Dp = 0.dp) =
    this.drawBehind {
    val borderSize = strokeWidth.toPx()
    val x = 0f + (borderSize / 2)
    drawLine(
        color = color,
        start = Offset(x, 0f),
        end = Offset(x, size.height - bottomOffset.toPx()),
        strokeWidth = borderSize
    )
}

fun Modifier.bottomBorder(color: Color, strokeWidth: Dp = 1.dp) = this.drawBehind {
    val borderSize = strokeWidth.toPx()
    val y = size.height - (borderSize / 2)
    drawLine(
        color = color,
        start = Offset(0f, y),
        end = Offset(size.width, y),
        strokeWidth = borderSize
    )
}