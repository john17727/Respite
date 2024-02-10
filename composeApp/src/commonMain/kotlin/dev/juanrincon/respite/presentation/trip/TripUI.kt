package dev.juanrincon.respite.presentation.trip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Trip

@Composable
fun TripUI(
    trip: Trip
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(trip.name)
    }
}