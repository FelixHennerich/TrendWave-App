import account.manager.LoginManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.AppModule
import event.TrendWaveViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import utilities.CommonLogger
import views.LoginScreen
import views.HomeScreen
import views.LoadingScreen
import views.RegisterScreen


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

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Loading) }
    var firstLogin by remember { mutableStateOf(true) }
    val loginScreenTT = LoginScreen()
    val loadingScreenTT = LoadingScreen()
    val homeScreenTT = HomeScreen()
    val registerScreenTT = RegisterScreen()
    val loginManager = LoginManager()


    val viewModel = getViewModel(
        key = "main-login-screen",
        factory = viewModelFactory {
            TrendWaveViewModel(appModule.imageDataSource)
        }
    )

    GlobalScope.launch {
        if(loginManager.isLoggedIn(appModule.localDataSource)){
            currentScreen = Screen.Home
        }else {
            if(firstLogin) {
                firstLogin = false
                currentScreen = Screen.Login
            }
        }
    }

    val state by viewModel.state.collectAsState()

    when (currentScreen) {
        is Screen.Loading -> loadingScreenTT.LoadingScreen(
            imageDataSource = appModule.imageDataSource
        )
        is Screen.Home -> homeScreenTT.HomeScreen(
            onEvent = viewModel::onEvent,
            state = state,
            localDataSource = appModule.localDataSource,
            imageDataSource = appModule.imageDataSource
        )
        is Screen.Login -> loginScreenTT.LoginScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateRegister = {currentScreen = Screen.Register},
            onNavigateHome = {currentScreen = Screen.Home},
            imageDataSource = appModule.imageDataSource,
            localDataManager = appModule.localDataSource
        )
        is Screen.Register -> registerScreenTT.RegisterScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateLogin = {currentScreen = Screen.Login},
            onNavigateHome = {currentScreen = Screen.Home},
            imageDataSource = appModule.imageDataSource,
            localDataManager = appModule.localDataSource
        )

    }
}

/**
 * define both screens as object
 *
 * @Object Home -> Main Screen, gonna be Login
 * @Object Login -> Login Screen
 * @Object Details -> Detail test screen
 */
sealed class Screen {
    data object Loading: Screen()
    data object Home : Screen()
    data object Login : Screen()
    data object Register: Screen()
}