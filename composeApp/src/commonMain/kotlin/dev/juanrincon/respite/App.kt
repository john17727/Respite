package dev.juanrincon.respite

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dev.juanrincon.core.presentation.theme.RespiteTheme
import dev.juanrincon.respite.navigation.createStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.compose.KoinContext

val store = CoroutineScope(SupervisorJob()).createStore()

@Composable
fun App() {
    RespiteTheme {
        KoinContext {
            Surface {
                /**
                 * Disable Voyager's hardware back press handling since we handle it ourselves.
                 * See [dev.juanrincon.respite.navigation.BackHandler]
                 * This decision was made to accommodate our custom animations on navigation. Voyager
                 * has their own transitions but are just your typical full page animations which
                 * didn't serve our purpose.
                 */
//                Navigator(TripScreen(), onBackPressed = null)
                val navController = rememberNavController()
                NavigationRoot(navController = navController)
            }
        }
    }
}