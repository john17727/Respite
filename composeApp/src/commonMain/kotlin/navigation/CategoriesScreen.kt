package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import domain.model.Category
import presentation.categories.CategoriesUI

class CategoriesScreen : Screen {
    @Composable
    override fun Content() {
//        val categoriesScreenModel =
        CategoriesUI(
            listOf(
                Category(1, "Temporary", "Description"),
                Category(2, "Gift", "Description"),
                Category(3, "Souvenir", "Description"),
                Category(4, "Clothing", null),
                Category(4, "Toiletries", null),
                Category(4, "Accessories", null),
            ),
            {},
            {}
        )
    }
}