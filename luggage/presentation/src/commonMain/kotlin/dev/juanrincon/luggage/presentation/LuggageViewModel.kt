package dev.juanrincon.luggage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juanrincon.categories.domain.CategoryRepository
import dev.juanrincon.luggage.domain.Item
import dev.juanrincon.luggage.domain.ItemRepository
import dev.juanrincon.luggage.presentation.LuggageItem.Companion.toEditingItem
import dev.juanrincon.luggage.presentation.LuggageItem.Companion.toUserItem
import dev.juanrincon.luggage.presentation.models.LuggageEvent
import dev.juanrincon.luggage.presentation.models.UICategory.Companion.toUICategory
import dev.juanrincon.luggage.presentation.models.UIItem.Companion.toUIItem
import dev.juanrincon.mvi.MVI
import dev.juanrincon.mvi.mvi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LuggageViewModel(
    private val repository: ItemRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel(), MVI<LuggageState, LuggageIntent, LuggageEvent> by mvi(LuggageState()) {

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
        LuggageIntent.NavigateBack -> navigateBack()
    }

    private fun navigateBack() {
        viewModelScope.launch {
            playClosingAnimation()
            emitSideEffect(LuggageEvent.NavigateBack)
        }
    }

    private suspend fun playClosingAnimation() {
        updateState { copy(listAnimation = false) }
        delay(125)
        updateState { copy(transitionAnimation = false) }
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
        mutableList.add(0, LuggageItem.newItem())
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
        viewModelScope.launch {
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
        viewModelScope.launch {
            updateState { copy(loading = true) }
            repository.read().fold(
                onSuccess = {
                    delay(50)
                    updateState {
                        copy(
                            luggage = it.map(::toLuggageItem),
                            loading = false,
                            inEditMode = false,
                            transitionAnimation = true
                        )
                    }
                    delay(100)
                    updateState { copy(listAnimation = true) }
                },
                onFailure = {
                    updateState { copy(loading = false, inEditMode = false) }
                }
            )
        }
    }

    private fun updateLuggage(id: Int, name: String, categoryId: Int) {
        viewModelScope.launch {
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
        viewModelScope.launch {
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
        viewModelScope.launch {
            updateState { copy(loading = true) }
            categoryRepository.read().fold(
                onSuccess = {
                    updateState { copy(categories = it.map { it.toUICategory() }, loading = false) }
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

    private fun toLuggageItem(item: Item): LuggageItem = LuggageItem.UserItem(item.toUIItem())
}