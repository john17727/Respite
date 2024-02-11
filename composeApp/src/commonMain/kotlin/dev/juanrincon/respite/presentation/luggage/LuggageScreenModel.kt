package dev.juanrincon.respite.presentation.luggage

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.model.Item
import dev.juanrincon.respite.domain.repository.ItemRepository
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.MVIDelegate
import kotlinx.coroutines.launch

class LuggageScreenModel(private val repository: ItemRepository) : ScreenModel,
    MVI<LuggageState, LuggageIntent, Nothing> by MVIDelegate(
        LuggageState()
    ) {

    init {
        getLuggage()
    }

    private fun getLuggage() {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.read().fold(
                onSuccess = {
                    updateState {
                        copy(
                            luggage = it.map(::toLuggageItem),
                            loading = false,
                            inEditMode = false
                        )
                    }
                },
                onFailure = {
                    updateState { copy(loading = false, inEditMode = false) }
                }
            )
        }
    }

    private fun toLuggageItem(item: Item): LuggageItem = LuggageItem.UserItem(item)
}