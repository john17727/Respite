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
import dev.juanrincon.mvi.mviHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LuggageViewModel(
    private val repository: ItemRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel(), MVI<LuggageState, LuggageIntent, LuggageEvent> by mviHandler(LuggageState()) {

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
            repository.delete(id).onFailure {
                updateState { copy(loading = false) }

            }
        }
    }

    private fun getLuggage() {
        updateState { copy(loading = true) }
        repository.read().onEach { items ->
            delay(50)
            updateState {
                copy(
                    luggage = items.map(::toLuggageItem),
                    loading = false,
                    inEditMode = false,
                    transitionAnimation = true
                )
            }
            delay(100)
            updateState { copy(listAnimation = true) }
        }.launchIn(viewModelScope)
    }

    private fun updateLuggage(id: Int, name: String, categoryId: Int) {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            repository.update(id, name, categoryId).onSuccess {
                updateState { copy(loading = false, inEditMode = false) }
            }.onFailure {
                updateState { copy(loading = false, inEditMode = false) }
            }
        }
    }

    private fun createLuggage(name: String, categoryId: Int) {
        updateState { copy(loading = true) }
        viewModelScope.launch {
            repository.create(name, categoryId).onSuccess {
                updateState { copy(loading = false, inAddMode = false) }
            }.onFailure {
                updateState { copy(loading = false, inAddMode = false) }
            }
        }
    }

    private fun getCategories() {
        updateState { copy(loading = true) }
        categoryRepository.read().onEach { categories ->
            val categoriesList = categories.map { it.toUICategory() }
            updateState { copy(categories = categoriesList, loading = false) }
        }.launchIn(viewModelScope)
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