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
                val navController = rememberNavController()
                NavigationRoot(navController = navController)
            }
        }
    }
}