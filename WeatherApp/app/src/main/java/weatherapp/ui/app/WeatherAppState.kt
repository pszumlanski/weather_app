package weatherapp.ui.app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * List of screens for [WeatherApp]
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    WeatherAppState(navController)
}

class WeatherAppState(val navController: NavHostController)