package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import presentation.categories.CategoriesUI

class CategoriesScreen : Screen {
    @Composable
    override fun Content() {
        CategoriesUI(
            listOf()
        )
    }
}