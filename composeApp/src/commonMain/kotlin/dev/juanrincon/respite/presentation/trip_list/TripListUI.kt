package dev.juanrincon.respite.presentation.trip_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.juanrincon.respite.domain.model.Trip
import dev.juanrincon.respite.presentation.components.TripCard

@Composable
fun TripListUI(
    trips: List<Trip>,
    navigateToTrip: (Int) -> Unit,
    createNewTask: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = createNewTask,
                icon = { Icon(Icons.Rounded.Add, "") },
                text = { Text("New Trip") })
        }
    ) {
        if (trips.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No Trips")
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Your Trips")
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(trips) { trip ->
                        TripCard(trip, onClick = navigateToTrip )
                    }
                }
            }
        }
    }
}