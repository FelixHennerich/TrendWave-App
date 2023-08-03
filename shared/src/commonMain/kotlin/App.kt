import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
/*@Composable
fun App() {
    var count by remember {
        mutableStateOf(0)
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = {
            count++
        }){
            Text("Count: $count")
        }
    }
}*/

expect fun getPlatformName(): String


@Composable
fun App() {
    val currentScreen = remember { mutableStateOf<Screen>(Screen.Home) }
    when (val screen = currentScreen.value) {
        is Screen.Home -> HomeScreen { currentScreen.value = Screen.Details }
        is Screen.Details -> DetailsScreen { currentScreen.value = Screen.Home }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: () -> Unit) {
    // Your home screen UI
    Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
        Button(onClick = onNavigateToDetails) {
            Text("Go to Details")
        }
    }
}

@Composable
fun DetailsScreen(onNavigateToHome: () -> Unit) {
    // Your details screen UI
    Button(onClick = onNavigateToHome) {
        Text("Go to Home")
    }
}

sealed class Screen {
    object Home : Screen()
    object Details : Screen()
}