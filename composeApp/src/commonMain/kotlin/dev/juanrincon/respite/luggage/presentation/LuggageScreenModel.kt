package dev.juanrincon.respite.luggage.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.MVIDelegate
import dev.juanrincon.respite.categories.domain.Category
import dev.juanrincon.respite.categories.domain.CategoryRepository
import dev.juanrincon.respite.luggage.domain.Item
import dev.juanrincon.respite.luggage.domain.ItemRepository
import dev.juanrincon.respite.luggage.presentation.LuggageItem.Companion.toEditingItem
import dev.juanrincon.respite.luggage.presentation.LuggageItem.Companion.toUserItem
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
        LuggageIntent.CancelCreateItem -> cancelCreateItem()
        is LuggageIntent.CancelEditItem -> cancelEditItem(intent.id)
        LuggageIntent.CreateItem -> addCreateItem()
        is LuggageIntent.CreateLuggage -> createLuggage(intent.name, intent.categoryId)
        is LuggageIntent.DeleteLuggage -> deleteLuggage(intent.id)
        is LuggageIntent.EditItem -> setEditItem(intent.id)
        LuggageIntent.GetLuggage -> getLuggage()
        is LuggageIntent.UpdateLuggage -> updateLuggage(intent.id, intent.name, intent.categoryId)
    }

    private fun setEditItem(id: Int) {
        updateState {
            copy(
                luggage = setItemEditable(this.luggage, id),
                inEditMode = true,
                inAddMode = false
            )
        }
    }

    private fun addCreateItem() {
        updateState {
            copy(
                luggage = addItemEditable(this.luggage),
                inEditMode = false,
                inAddMode = true
            )
        }
    }

    private fun cancelCreateItem() {
        updateState {
            copy(
                luggage = removeEditable(luggage),
                inEditMode = false,
                inAddMode = false
            )
        }
    }

    private fun removeEditable(luggage: List<LuggageItem>): List<LuggageItem> =
        luggage.find { it.id == 0 }?.let { item ->
            val mutableList = luggage.toMutableList()
            mutableList.remove(item)
            mutableList
        } ?: luggage

    private fun addItemEditable(luggage: List<LuggageItem>): List<LuggageItem> {
        val mutableList = luggage.toMutableList()
        mutableList.add(0, LuggageItem.CreatingItem(Item(0, "", Category(0, "", null))))
        return mutableList
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

    private fun updateLuggage(id: Int, name: String, categoryId: Int) {
        screenModelScope.launch {
            updateState { copy(loading = true) }
            repository.update(id, name, categoryId).fold(
                onSuccess = { getLuggage() },
                onFailure = {
                    updateState { copy(loading = false, inEditMode = false) }
                }
            )
        }
    }

    private fun createLuggage(name: String, categoryId: Int) {
        updateState { copy(loading = true) }
        screenModelScope.launch {
            repository.create(name, categoryId).fold(
                onSuccess = {
                    getLuggage()
                },
                onFailure = {
                    updateState { copy(loading = false) }
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

    private fun cancelEditItem(id: Int) {
        updateState {
            copy(
                luggage = setEditableItemToUserItem(luggage, id),
                inEditMode = false
            )
        }
    }

    private fun setEditableItemToUserItem(luggage: List<LuggageItem>, id: Int): List<LuggageItem> =
        luggage.find { it.id == id }?.let { item ->
            val mutableList = luggage.toMutableList()
            val indexOfEditable = luggage.indexOf(item)
            mutableList[indexOfEditable] = (item as LuggageItem.EditingItem).toUserItem()
            mutableList
        } ?: luggage

    private fun toLuggageItem(item: Item): LuggageItem = LuggageItem.UserItem(item)
}