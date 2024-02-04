import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import domain.model.Trip
import presentation.`trip-list`.TripListScreen

@Composable
fun App() {
    MaterialTheme {
        TripListScreen(
            listOf(
                Trip(1, "Las Vegas", 30),
                Trip(2, "Roswell", 30),
                Trip(1, "El Paso", 30),
            ),
            {},
            {}
        )
    }
}