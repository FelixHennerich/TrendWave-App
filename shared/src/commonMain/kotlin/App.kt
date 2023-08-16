import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import views.DetailScreen
import views.HomeScreen
import views.SettingsView

/**
 * fun App() is the main function of the "Shared" code
 * IOS and Android do have different main methods, but those are not needed to edit
 */
@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val detailScreenTT = DetailScreen()
    val settingsViewTT = SettingsView()
    val homeScreenTT = HomeScreen()

    when (currentScreen) {
        is Screen.Home -> homeScreenTT.HomeScreen(
            onNavigateToDetails = { currentScreen = Screen.Details },
            onNavigateToSettings = { currentScreen = Screen.Settings }
        )
        is Screen.Details -> detailScreenTT.DetailsScreen(
            onNavigateToHome = { currentScreen = Screen.Home }
        )
        is Screen.Settings -> settingsViewTT.SettingsScreen(
            onNavigateToHome = { currentScreen = Screen.Home }
        )
    }
}

/**
 * define both screens as object
 *
 * @Object Home -> Main Screen, gonna be Login
 * @Object Details -> Detail test screen
 * @Object Settings -> Settings screen
 */
sealed class Screen {
    data object Home : Screen()
    data object Details : Screen()
    data object Settings: Screen()
}