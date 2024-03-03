package dev.juanrincon.respite.presentation.trips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Luggage
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.presentation.components.CallToActionTag
import dev.juanrincon.respite.presentation.components.LeftActionButton
import dev.juanrincon.respite.presentation.components.RightActionButton

@Composable
fun TripsUI(
    trip: Trip?,
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit
) {
    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        if (trip != null) {
            TODO()
        } else {
            CallToActionTag(
                callToActionText = "Adventure Awaits!",
                backgroundColor = Color(0xFF2E6C82),
                modifier = Modifier.align(Alignment.Center).fillMaxWidth().fillMaxHeight(.6f)
                    .padding(horizontal = 36.dp)
            )
        }
        LeftActionButton(
            onClick = onCategoriesClick,
            backgroundColor = Color(0xFFA6C994),
            contentColor = Color(0xFF3C422F),
            icon = Icons.Rounded.Sell,
            modifier = Modifier.align(Alignment.BottomStart)
        )
        RightActionButton(
            onClick = onLuggageClick,
            backgroundColor = Color(0xFFEDD379),
            contentColor = Color(0xFF684633),
            icon = Icons.Rounded.Luggage,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}