import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import event.EventManager
import event.listeners.HTTPListener
import views.LoginScreen
import views.HomeScreen
import views.RegisterScreen
import views.SettingsView


/**
 * fun App() is the main function of the "Shared" code
 * IOS and Android do have different main methods, but those are not needed to edit
 */
@Composable
fun App() {
    registerListener()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val loginScreenTT = LoginScreen()
    val settingsViewTT = SettingsView()
    val homeScreenTT = HomeScreen()
    val registerScreenTT = RegisterScreen()

    when (currentScreen) {
        is Screen.Home -> homeScreenTT.HomeScreen(
            onNavigateToLogin = { currentScreen = Screen.Login },
            onNavigateToSettings = { currentScreen = Screen.Settings },
            onNavigateToRegister = {currentScreen = Screen.Register}
        )
        is Screen.Login -> loginScreenTT.LoginScreen(
        )
        is Screen.Register -> registerScreenTT.RegisterScreen(

        )
        is Screen.Settings -> settingsViewTT.SettingsScreen(
            onNavigateToHome = { currentScreen = Screen.Home }
        )

    }
}

private val eventManager = EventManager()

/**
 * Get Main EventManager
 * @return -> eventmanager
 */
fun getEventManager(): EventManager{
    return eventManager
}

/**
 * Register all listeners that are used
 */
fun registerListener(){
    //Add every event
    val httplistener = HTTPListener()

    //Add every event
    eventManager.addListener(httplistener)
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