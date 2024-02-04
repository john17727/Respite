import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import domain.model.Trip
import presentation.`trip-list`.TripListScreen
import presentation.trip.TripScreen

@Composable
fun App() {
    MaterialTheme {
        TripScreen(
            Trip(3, "El Paso", 30)
        )
//        TripListScreen(
//            listOf(
//                Trip(1, "Las Vegas", 30),
//                Trip(2, "Roswell", 30),
//                Trip(3, "El Paso", 30),
//            ),
//            {},
//            {}
//        )
    }
}