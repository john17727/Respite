import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import navigation.CategoriesScreen
import presentation.theme.RespiteTheme

@Composable
fun App() {
    RespiteTheme {
        Surface {
            Navigator(CategoriesScreen())
        }
    }
}