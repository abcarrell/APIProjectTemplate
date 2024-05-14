package io.github.abcarrell.apiproject.stubs

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun mviStub(packageName: String) = """
package ${escapeKotlinIdentifier(packageName)}.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

interface MVI<UiState, UiAction, SideEffect> {
    val state: StateFlow<UiState>
    val effects: Flow<SideEffect>

    fun onAction(uiAction: UiAction)
}

interface MVIActor<UiState, UiAction, SideEffect> : MVI<UiState, UiAction, SideEffect> {
    fun setState(reduce: UiState.() -> UiState)

    fun setState(state: UiState)

    fun setEffect(effect: SideEffect)

    fun setEffect(effect: () -> SideEffect)
}

private class MVIDelegate<UiState, UiAction, SideEffect>(
    initialUiState: UiState
) : MVIActor<UiState, UiAction, SideEffect> {
    private val _uiState = MutableStateFlow(initialUiState)
    override val state: StateFlow<UiState> = _uiState.asStateFlow()

    private val _sideEffect: Channel<SideEffect> by lazy {
        Channel(capacity = Channel.UNLIMITED)
    }
    override val effects: Flow<SideEffect> = _sideEffect.receiveAsFlow()

    override fun onAction(uiAction: UiAction) {}

    override fun setState(reduce: UiState.() -> UiState) {
        _uiState.update(reduce)
    }

    override fun setState(state: UiState) {
        _uiState.update { state }
    }

    override fun setEffect(effect: SideEffect) {
        _sideEffect.trySend(effect)
    }

    override fun setEffect(effect: () -> SideEffect) {
        _sideEffect.trySend(effect())
    }
}

fun <UiState, UiAction, SideEffect> mvi(
    initialUiState: UiState
): MVIActor<UiState, UiAction, SideEffect> = MVIDelegate(initialUiState)

"""