package dev.juanrincon.respite.trips.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.juanrincon.respite.trips.domain.Trip
import dev.juanrincon.respite.trips.domain.TripItem
import dev.juanrincon.respite.trips.domain.TripStatus
import dev.juanrincon.respite.trips.presentation.pages.EmptyPage
import dev.juanrincon.respite.trips.presentation.pages.PackForDestinationPage

@Composable
fun TripsUI(
    state: TripState,
    onCreateNewTrip: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit,
    onAddItemCountClick: (tripId: Int, item: TripItem) -> Unit,
    onRemoveItemCountClick: (tripId: Int, item: TripItem) -> Unit,
    onCancelPackingClick: (tripId: Int, status: TripStatus) -> Unit,
    onFinishPackingClick: (trip: Trip) -> Unit,
) {
    if (state.trip != null) {
        when (state.trip.status) {
            TripStatus.Destination -> Text("Destination Screen coming soon!")
            TripStatus.PackingDestination -> PackForDestinationPage(
                destination = state.trip.nameAbbr.uppercase(),
                items = state.trip.items,
                onAddClick = { item -> onAddItemCountClick(state.trip.id, item) },
                onRemoveClick = { item -> onRemoveItemCountClick(state.trip.id, item) },
                onCancelPacking = { onCancelPackingClick(state.trip.id, state.trip.status) },
                onFinishPackingClick = { onFinishPackingClick(state.trip) }
            )

            TripStatus.PackingNextDestination -> TODO()
        }
    } else {
        EmptyPage(
            onSaveNewTrip = onCreateNewTrip,
            onCategoriesClick = onCategoriesClick,
            onLuggageClick = onLuggageClick
        )
    }
}
