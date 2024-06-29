package dev.juanrincon.trips.presentation.empty_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.juanrincon.core.presentation.components.LeftActionButton
import dev.juanrincon.core.presentation.components.RightActionButton
import dev.juanrincon.core.presentation.utils.ObserveAsEvents
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import respite.trips.presentation.generated.resources.Res
import respite.trips.presentation.generated.resources.travelers_yellow

@OptIn(KoinExperimentalAPI::class)
@Composable
fun EmptyScreenRoot(
    onCategoriesClick: () -> Unit,
    onLuggageClick: () -> Unit,
    onNavigateToPackForDestination: (Int) -> Unit,
    onNavigateToDestination: (Int) -> Unit,
    onNavigateToNextDestination: (Int) -> Unit,
    viewModel: EmptyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    ObserveAsEvents(viewModel.sideEffect) { event ->
        when (event) {
            is EmptyScreenEvent.Destination -> onNavigateToDestination(event.tripId)
            is EmptyScreenEvent.PackForDestination -> onNavigateToPackForDestination(event.tripId)
            is EmptyScreenEvent.PackForNextDestination -> onNavigateToNextDestination(event.tripId)
        }
    }
    EmptyScreenRootScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                EmptyScreenIntent.NavigateToCategories -> onCategoriesClick()
                EmptyScreenIntent.NavigateToLuggage -> onLuggageClick()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@Composable
private fun EmptyScreenRootScreen(
    state: EmptyScreenState,
    onIntent: (EmptyScreenIntent) -> Unit
) {
    var showCreationTag by remember { mutableStateOf(false) }
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.07f))
            Text(
                "ADVENTURE AWAITS!",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                color = Color(0xFF684633),
                modifier = Modifier.padding(end = 16.dp)
                    .background(
                        color = Color(0xFFEDD379),
                        shape = CutCornerShape(topEnd = 24.dp)
                    )
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
            )
            Image(
                painterResource(Res.drawable.travelers_yellow),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors().copy(
                    contentColor = Color(0xFFEDD379),
                    containerColor = Color(0xFF684633)
                ),
                shape = CutCornerShape(bottomStart = 8.dp),
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
            ) {
                Text("START PACKING", modifier = Modifier.padding(end = 16.dp))
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            LeftActionButton(
                onClick = { onIntent(EmptyScreenIntent.NavigateToCategories) },
                backgroundColor = Color(0xFFA6C994),
                contentColor = Color(0xFF3C422F),
                icon = Icons.Rounded.Sell,
            )
            RightActionButton(
                onClick = { onIntent(EmptyScreenIntent.NavigateToLuggage) },
                backgroundColor = Color(0xFFEDD379),
                contentColor = Color(0xFF684633),
                icon = Icons.Rounded.Inbox,
            )
        }
    }
}