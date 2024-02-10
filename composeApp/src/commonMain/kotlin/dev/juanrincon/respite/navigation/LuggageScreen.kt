package dev.juanrincon.respite.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.juanrincon.respite.domain.model.Category
import dev.juanrincon.respite.domain.model.Item
import dev.juanrincon.respite.presentation.luggage.LuggageItem
import dev.juanrincon.respite.presentation.luggage.LuggageUI

class LuggageScreen : Screen {

    @Composable
    override fun Content() {
        LuggageUI(
            listOf(
                LuggageItem.UserItem(Item(1, "Shirt", Category(1, "Clothing", null))),
                LuggageItem.UserItem(Item(2, "Pants", Category(1, "Clothing", null))),
                LuggageItem.UserItem(Item(3, "Phone", Category(2, "Devices", null))),
            )
        )
    }
}