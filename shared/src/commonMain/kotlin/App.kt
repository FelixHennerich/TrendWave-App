import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import views.DetailScreen
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

    when (currentScreen) {
        is Screen.Home -> HomeScreen(
            onNavigateToDetails = { currentScreen = Screen.Details },
            onNavigateToSettings = { currentScreen = Screen.Settings }
        )
        is Screen.Details -> detailScreenTT.DetailsScreen(
            onNavigateToHome = { currentScreen = Screen.Home }
        )
        is Screen.Settings -> settingsViewTT.SettingsScreen(
            onNavigateToHome = { currentScreen = Screen.Home }
        )

        else -> {}
    }
}

/**
 * HOME SCREEN
 *
 * WILL BE OUTSOURCED AS SOON AS @Caaasperrr has the login screen
 */
@Composable
fun HomeScreen(onNavigateToDetails: () -> Unit, onNavigateToSettings: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onNavigateToDetails) {
            Text("Go to Details")
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
        Button(onClick = onNavigateToSettings) {
            Text("Go to Settings")
        }
    }
}

/**
 * define both screens as object
 */
sealed class Screen {
    object Home : Screen()
    object Details : Screen()
    object Settings: Screen()
}

expect fun getPlatformName(): String