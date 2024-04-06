package dev.juanrincon.respite

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import dev.juanrincon.respite.common.presentation.theme.RespiteTheme
import dev.juanrincon.respite.navigation.TripScreen
import dev.juanrincon.respite.navigation.createStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

val store = CoroutineScope(SupervisorJob()).createStore()

@Composable
fun App() {
    RespiteTheme {
        Surface {
            /**
             * Disable Voyager's hardware back press handling since we handle it ourselves.
             * See [dev.juanrincon.respite.navigation.BackHandler]
             * This decision was made to accommodate our custom animations on navigation. Voyager
             * has their own transitions but are just your typical full page animations which
             * didn't serve our purpose.
             */
            /**
             * Disable Voyager's hardware back press handling since we handle it ourselves.
             * See [dev.juanrincon.respite.navigation.BackHandler]
             * This decision was made to accommodate our custom animations on navigation. Voyager
             * has their own transitions but are just your typical full page animations which
             * didn't serve our purpose.
             */
            Navigator(TripScreen(), onBackPressed = null)
        }
    }
}