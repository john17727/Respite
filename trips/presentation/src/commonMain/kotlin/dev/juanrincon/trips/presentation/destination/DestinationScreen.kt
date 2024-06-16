package dev.juanrincon.trips.presentation.destination

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DestinationScreenRoot(
    viewModel: DestinationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    DestinationScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun DestinationScreen(
    state: DestinationState,
    onIntent: (DestinationIntent) -> Unit
) {
    Text("Destination")
}