package presentation.trip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Trip

@Composable
fun TripUI(
    trip: Trip
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(trip.name, style = MaterialTheme.typography.h2)
    }
}