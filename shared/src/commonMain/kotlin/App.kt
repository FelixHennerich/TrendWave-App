import account.manager.LoginManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.AppModule
import event.TrendWaveEvent
import event.TrendWaveViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import post.RESTfulPostManager
import views.LoginScreen
import views.HomeScreen
import views.LoadingScreen
import views.RegisterScreen
import views.presentation.PostButtonManager


/**
 * fun App() is the main function of the "Shared" code
 * IOS and Android do have different main methods, but those are not needed to edit
 *
 * @param appModule -> iOS or Android AppMoudle for ImageDataSources
 */
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun App(
    appModule: AppModule
){

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Loading) }
    var loggedin by remember { mutableStateOf(false) }
    val loginScreenTT = LoginScreen()
    val loadingScreenTT = LoadingScreen()
    val homeScreenTT = HomeScreen()
    val registerScreenTT = RegisterScreen()

    val viewModel = getViewModel(
        key = "main-login-screen",
        factory = viewModelFactory {
            val restAPI = RESTfulPostManager()
            TrendWaveViewModel(
                localDataStorageManager = appModule.localDataSource,
                restApi = restAPI
            )
        }
    )
    val state by viewModel.state.collectAsState()
    val loginManager = LoginManager()

    //Application start event
    viewModel.onEvent(TrendWaveEvent.ApplicationStartEvent)



    //Is logged in?
    GlobalScope.launch {
        delay(100)
        if(!loggedin) {
            loggedin = true
            if (loginManager.isLoggedIn(appModule.localDataSource)) {
                PostButtonManager().getButtonsDatabase(
                    appModule.localDataSource.readString("uuid")!!, viewModel::onEvent, false).toMutableList()
                while(state.posts.isEmpty() || state.user == null || !state.buttonshomescreenloaded){
                    delay(10)
                }

                //Set screen to home
                currentScreen = Screen.Home
            } else {
                //Set screen to Login
                currentScreen = Screen.Login
            }
        }
    }


    when (currentScreen) {
        //Loading Screen Navigation
        is Screen.Loading -> loadingScreenTT.LoadingScreen(
            imageDataSource = appModule.imageDataSource
        )

        //Home Screen Navigation
        is Screen.Home -> homeScreenTT.HomeScreen(
            onEvent = viewModel::onEvent,
            state = state,
            localDataSource = appModule.localDataSource,
            onNavigateLogin = {currentScreen = Screen.Login},
        )

        //Login Screen Navigation
        is Screen.Login -> loginScreenTT.LoginScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateRegister = {currentScreen = Screen.Register},
            onNavigateHome = {currentScreen = Screen.Home},
            imageDataSource = appModule.imageDataSource,
            localDataManager = appModule.localDataSource
        )

        //Register Screen Navigation
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
 * @Object Loading -> Screen while starting the app
 * @Object Home -> Screen after login or start the app
 * @Object Login -> Enter user data to login
 * @Object Register -> Create an account
 */
sealed class Screen {
    data object Loading: Screen()
    data object Home : Screen()
    data object Login : Screen()
    data object Register: Screen()
}