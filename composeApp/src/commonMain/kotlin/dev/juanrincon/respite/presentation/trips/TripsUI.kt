package dev.juanrincon.respite.presentation.trips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.juanrincon.respite.presentation.components.LeftActionButton
import dev.juanrincon.respite.presentation.components.RightActionButton

@Composable
fun TripsUI() {
    Box(modifier = Modifier.fillMaxSize()) {
        LeftActionButton(
            onClick = {},
            containerColor = Color(0xFFA6C994),
            contentColor = Color(0xFF3C422F),
            icon = Icons.Rounded.Sell,
            modifier = Modifier.align(Alignment.BottomStart)
        )
        RightActionButton(
            onClick = {},
            containerColor = Color(0xFFEDD379),
            contentColor = Color(0xFF684633),
            icon = Icons.Rounded.Luggage,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}