package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Trip

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TripCard(trip: Trip, onClick: (Int) -> Unit) {
    Card(onClick = { onClick(trip.id) }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = trip.name, style = MaterialTheme.typography.h4)
            Text(text = trip.totalItems.toString(), style = MaterialTheme.typography.body1)
        }
    }
}