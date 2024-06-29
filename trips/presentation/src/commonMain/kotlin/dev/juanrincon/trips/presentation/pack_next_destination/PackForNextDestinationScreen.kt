package dev.juanrincon.trips.presentation.pack_next_destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.ParametersHolder

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PackForeNextDestinationScreenRoot(
    onNavigateBack: () -> Unit,
    parametersHolder: ParametersHolder,
    viewModel: PackForNextDestinationViewModel = koinViewModel(parameters = { parametersHolder })
) {
    PackForeNextDestinationScreen(
        state = viewModel.state.collectAsState(),
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun PackForeNextDestinationScreen(
    state: State<PackForNextDestinationState>,
    onIntent: (PackForNextDestinationIntent) -> Unit
) {

}