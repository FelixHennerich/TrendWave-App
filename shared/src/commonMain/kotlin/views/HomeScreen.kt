package views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class HomeScreen {

    /**
     * HOME SCREEN
     *
     * WILL BE OUTSOURCED AS SOON AS @Caaasperrr has the login screen
     *
     * @param onNavigateToDetails -> naviagte to Detail screen
     * @param onNavigateToSettings -> naviagte to Settings screen
     */
    @Composable
    fun HomeScreen(onNavigateToLogin: () -> Unit, onNavigateToSettings: () -> Unit) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = onNavigateToLogin) {
                Text("Go to Details")
            }
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
            Button(onClick = onNavigateToSettings) {
                Text("Go to Settings")
            }
        }
    }

}