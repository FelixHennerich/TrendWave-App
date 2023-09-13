import account.image.ImageDownloader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.AppModule
import event.TrendWaveViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import views.LoginScreen
import views.HomeScreen
import views.RegisterScreen
import views.SettingsScreen


/**
 * fun App() is the main function of the "Shared" code
 * IOS and Android do have different main methods, but those are not needed to edit
 *
 * @param appModule -> iOS or Android AppMoudle for ImageDataSources
 */
@Composable
fun App(
    appModule: AppModule
){


    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val loginScreenTT = LoginScreen()
    val settingsScreenTT = SettingsScreen()
    val homeScreenTT = HomeScreen()
    val registerScreenTT = RegisterScreen()


    val viewModel = getViewModel(
        key = "main-login-screen",
        factory = viewModelFactory {
            TrendWaveViewModel(appModule.imageDataSource)
        }
    )

    val state by viewModel.state.collectAsState()

    when (currentScreen) {
        is Screen.Home -> homeScreenTT.HomeScreen(
            onNavigateToLogin = { currentScreen = Screen.Login },
            onNavigateToSettings = { currentScreen = Screen.Settings },
            onNavigateToRegister = {currentScreen = Screen.Register}
        )
        is Screen.Login -> loginScreenTT.LoginScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateRegister = {currentScreen = Screen.Register},
            onNavigateHome = {currentScreen = Screen.Home},
            imageDataSource = appModule.imageDataSource
        )
        is Screen.Register -> registerScreenTT.RegisterScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateLogin = {currentScreen = Screen.Login},
            onNavigateHome = {currentScreen = Screen.Home},
            imageDataSource = appModule.imageDataSource
        )
        is Screen.Settings -> settingsScreenTT.SettingsScreen(
            onNavigateToHome = { currentScreen = Screen.Home },
            imageDataSource = appModule.imageDataSource
        )

    }
}

/**
 * define both screens as object
 *
 * @Object Home -> Main Screen, gonna be Login
 * @Object Login -> Login Screen
 * @Object Details -> Detail test screen
 * @Object Settings -> Settings screen
 */
sealed class Screen {
    data object Home : Screen()
    data object Login : Screen()
    data object Settings: Screen()
    data object Register: Screen()
}