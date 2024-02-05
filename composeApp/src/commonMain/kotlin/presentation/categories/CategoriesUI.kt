package presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.Category
import presentation.components.BannerAlignment
import presentation.components.FeatureBanner
import presentation.components.VerticalText
import presentation.extensions.vertical

@Composable
fun CategoriesUI(
    categories: List<Category>
) {
    Row(modifier = Modifier.fillMaxSize()) {
        FeatureBanner(
            BannerAlignment.Start,
            modifier = Modifier.clip(CutCornerShape(topEnd = 16.dp)).background(Color(0xFFA6C994))
                .padding(vertical = 16.dp, horizontal = 10.dp)
        ) { degrees ->
            VerticalText(
                text = "Categories",
                degrees = degrees,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(32.dp))
            Icon(Icons.Rounded.Sell, null)
        }
    }
}
