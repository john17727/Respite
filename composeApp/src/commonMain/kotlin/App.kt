import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import data.DriverFactory
import data.createDatabase
import dev.juanrincon.respite.Database
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