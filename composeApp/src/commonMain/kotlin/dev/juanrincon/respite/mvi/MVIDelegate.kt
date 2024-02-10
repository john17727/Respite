package dev.juanrincon.respite.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MVIDelegate<State, Intent, SideEffect> internal constructor(
    initialUiState: State,
) : MVI<State, Intent, SideEffect> {

    private val _state = MutableStateFlow(initialUiState)
    override val state: StateFlow<State> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    override fun onIntent(intent: Intent) {}

    override fun updateState(newState: State) {
        _state.update { newState }
    }

    override fun updateState(block: State.() -> State) {
        _state.update(block)
    }

    override fun CoroutineScope.emitSideEffect(effect: SideEffect) {
        this.launch { _sideEffect.send(effect) }
    }
}

fun <UiState, UiAction, SideEffect> mvi(
    initialUiState: UiState,
): MVI<UiState, UiAction, SideEffect> = MVIDelegate(initialUiState)