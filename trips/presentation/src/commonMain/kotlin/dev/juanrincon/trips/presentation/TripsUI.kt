package dev.juanrincon.trips.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.juanrincon.trips.presentation.models.TripState
import dev.juanrincon.trips.presentation.models.UITrip
import dev.juanrincon.trips.presentation.models.UITripItem
import dev.juanrincon.trips.presentation.models.UITripStatus
import dev.juanrincon.trips.presentation.pages.EmptyPage
import dev.juanrincon.trips.presentation.pages.PackForDestinationPage

@Composable
fun TripsUI(
    state: TripState,
    onCreateNewTrip: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit,
    onAddItemCountClick: (tripId: Int, item: UITripItem) -> Unit,
    onRemoveItemCountClick: (tripId: Int, item: UITripItem) -> Unit,
    onCancelPackingClick: (tripId: Int, status: UITripStatus) -> Unit,
    onFinishPackingClick: (trip: UITrip) -> Unit,
) {
    if (state.trip != null) {
        when (state.trip.status) {
            UITripStatus.Destination -> Text("Destination Screen coming soon!")
            UITripStatus.PackingDestination -> PackForDestinationPage(
                destination = state.trip.name.uppercase(),
                items = state.trip.items,
                onAddClick = { item -> onAddItemCountClick(state.trip.id, item) },
                onRemoveClick = { item -> onRemoveItemCountClick(state.trip.id, item) },
                onCancelPacking = { onCancelPackingClick(state.trip.id, state.trip.status) },
                onFinishPackingClick = { onFinishPackingClick(state.trip) }
            )

            UITripStatus.PackingNextDestination -> TODO()
        }
    } else {
        EmptyPage(
            onSaveNewTrip = onCreateNewTrip,
            onCategoriesClick = onCategoriesClick,
            onLuggageClick = onLuggageClick
        )
    }
}
