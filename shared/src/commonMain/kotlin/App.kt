import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

expect fun getPlatformName(): String

/**
 * fun App() is the main function of the "Shared" code
 * IOS and Android do have different main methods, but those are not needed to edit
 */
@Composable
fun App() {
    val currentScreen = remember { mutableStateOf<Screen>(Screen.Home) }
    when (val screen = currentScreen.value) {
        is Screen.Home -> HomeScreen { currentScreen.value = Screen.Details }
        is Screen.Details -> DetailsScreen { currentScreen.value = Screen.Home }
    }
}

/**
 * Testview 1 (home)
 */
@Composable
fun HomeScreen(onNavigateToDetails: () -> Unit) {
    Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
        Button(onClick = onNavigateToDetails) {
            Text("Go to Details")
        }
    }
}

/**
 * Testview 2 (detail)
 * counter button
 */
@Composable
fun DetailsScreen(onNavigateToHome: () -> Unit) {
    Button(onClick = onNavigateToHome) {
        Text("Go to Home")
    }
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
}

/**
 * define both screens as object
 */
sealed class Screen {
    object Home : Screen()
    object Details : Screen()
}