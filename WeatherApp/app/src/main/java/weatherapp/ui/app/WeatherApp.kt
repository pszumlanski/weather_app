package weatherapp.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import weatherapp.ui.home.Home

@Composable
fun WeatherApp(
    appState: WeatherAppState = rememberAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { Home() }
    }
}