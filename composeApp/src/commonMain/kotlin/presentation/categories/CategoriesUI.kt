package presentation.categories

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import domain.model.Category
import presentation.components.VerticalBanner

@Composable
fun CategoriesUI(
    categories: List<Category>
) {
    Row(modifier = Modifier.fillMaxSize()) {
        VerticalBanner(
            text = "Categories",
            icon = Icons.Rounded.Sell,
            backgroundColor = Color(0xFFA6C994),
            contentColor = Color(0xFF3C422F),
        )
    }
}
