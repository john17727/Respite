package presentation.trip_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Trip
import presentation.components.TripCard

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
                Text("Your Trips", style = MaterialTheme.typography.h3)
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