import account.AppUser
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import post.RESTfulPostManager
import post.Post
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
    var loggedin by remember { mutableStateOf(false) }
    var lst by remember { mutableStateOf<List<Post>>(emptyList()) }
    var lst1 by remember { mutableStateOf<List<Post>>(emptyList()) }
    val loginScreenTT = LoginScreen()
    val loadingScreenTT = LoadingScreen()
    val homeScreenTT = HomeScreen()
    val registerScreenTT = RegisterScreen()


    val viewModel = getViewModel(
        key = "main-login-screen",
        factory = viewModelFactory {
            TrendWaveViewModel(appModule.imageDataSource)
        }
    )
    val state by viewModel.state.collectAsState()
    val loginManager = LoginManager(state)

    GlobalScope.launch {
        delay(100)
        if(loginManager.isLoggedIn(appModule.localDataSource)){
            if(!loggedin) {
                loggedin = true
                if (appModule.localDataSource.readString("uuid") != null) {
                    val uuid = appModule.localDataSource.readString("uuid").toString()
                    if(state.user == null){
                        val user = AppUser(state)
                        viewModel.onEvent(TrendWaveEvent.LoadUserToLocal(user.getUser(uuid)))
                    }
                    val restAPI = RESTfulPostManager(state)

                    lst = restAPI.getUserPosts(uuid)
                    lst1 = restAPI.getRandomPosts()

                    while(lst1.isEmpty() && lst.isEmpty()){
                        delay(1)
                    }
                    state.user?.let { TrendWaveEvent.UserPostLoading(lst1, lst, uuid, it.follower, state.user!!.following) }
                        ?.let { viewModel.onEvent(it) }
                    while (state.posts.isEmpty()) {
                        delay(1)
                    }
                    currentScreen = Screen.Home
                 }
            }
        }else {
            currentScreen = Screen.Login
        }
    }

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