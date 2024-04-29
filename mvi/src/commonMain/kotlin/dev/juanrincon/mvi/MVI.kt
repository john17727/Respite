package dev.juanrincon.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVI<State, Intent, SideEffect> {
    val state: StateFlow<State>
    val sideEffect: Flow<SideEffect>

    fun onIntent(intent: Intent)

    fun updateState(block: State.() -> State)

    fun updateState(newState: State)

    fun CoroutineScope.emitSideEffect(effect: SideEffect)
}