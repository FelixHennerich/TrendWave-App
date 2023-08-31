import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import event.TrendWaveState
import event.TrendWaveViewModel
import views.LoginScreen
import views.HomeScreen
import views.RegisterScreen
import views.SettingsScreen


/**
 * fun App() is the main function of the "Shared" code
 * IOS and Android do have different main methods, but those are not needed to edit
 */
@Composable
fun App() {

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val loginScreenTT = LoginScreen()
    val settingsScreenTT = SettingsScreen()
    val homeScreenTT = HomeScreen()
    val registerScreenTT = RegisterScreen()

    val viewModel = getViewModel(
        key = "main-login-screen",
        factory = viewModelFactory {
            TrendWaveViewModel()
        }
    )

    when (currentScreen) {
        is Screen.Home -> homeScreenTT.HomeScreen(
            onNavigateToLogin = { currentScreen = Screen.Login },
            onNavigateToSettings = { currentScreen = Screen.Settings },
            onNavigateToRegister = {currentScreen = Screen.Register}
        )
        is Screen.Login -> loginScreenTT.LoginScreen(TrendWaveState(), viewModel::onEvent)
        is Screen.Register -> registerScreenTT.RegisterScreen(

        )
        is Screen.Settings -> settingsScreenTT.SettingsScreen(
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
    data object Login : Screen()
    data object Settings: Screen()
    data object Register: Screen()
}