package dev.juanrincon.respite.presentation.luggage

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.respite.domain.model.Item
import dev.juanrincon.respite.domain.repository.CategoryRepository
import dev.juanrincon.respite.domain.repository.ItemRepository
import dev.juanrincon.respite.mvi.MVI
import dev.juanrincon.respite.mvi.MVIDelegate
import dev.juanrincon.respite.presentation.luggage.LuggageItem.Companion.toEditingItem
import kotlinx.coroutines.launch

class LuggageScreenModel(
    private val repository: ItemRepository,
    private val categoryRepository: CategoryRepository
) : ScreenModel,
    MVI<LuggageState, LuggageIntent, Nothing> by MVIDelegate(
        LuggageState()
    ) {

    init {
        getCategories()
        getLuggage()
    }

    override fun onIntent(intent: LuggageIntent) = when (intent) {
        LuggageIntent.CancelCreateItem -> TODO()
        is LuggageIntent.CancelEditItem -> TODO()
        LuggageIntent.CreateItem -> TODO()
        is LuggageIntent.CreateLuggage -> TODO()
        is LuggageIntent.DeleteLuggage -> deleteLuggage(intent.id)
        is LuggageIntent.EditItem -> setEditItem(intent.id)
        LuggageIntent.GetLuggage -> TODO()
        is LuggageIntent.UpdateLuggage -> TODO()
    }

    private fun setEditItem(id: Int) {
        updateState {
            copy(
                luggage = setItemEditable(this.luggage, id),
                inEditMode = true
            )
        }
    }

    private fun setItemEditable(luggage: List<LuggageItem>, id: Int): List<LuggageItem> =
        luggage.find { it.id == id }?.let { item ->
            val mutableList = luggage.toMutableList()
            val indexOfEditable = luggage.indexOf(item)
            mutableList[indexOfEditable] = (item as LuggageItem.UserItem).toEditingItem()
            mutableList
        } ?: luggage

    private fun deleteLuggage(id: Int) {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.delete(id).fold(
                onSuccess = {
                    getLuggage()
                },
                onFailure = {
                    updateState { copy(loading = false) }
                }
            )
        }
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

    private fun getCategories() {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            categoryRepository.read().fold(
                onSuccess = {
                    updateState { copy(categories = it) }
                },
                onFailure = {
                    updateState { copy(loading = false) }
                }
            )
        }
    }

    private fun toLuggageItem(item: Item): LuggageItem = LuggageItem.UserItem(item)
}