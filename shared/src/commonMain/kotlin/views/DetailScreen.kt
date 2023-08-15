package views

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

class DetailScreen {

    /**
     * Testview 2 (detail)
     * counter button
     * @param onNavigateToHome -> Navigator button
     */
    @Composable
    fun DetailsScreen(onNavigateToHome: () -> Unit) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = onNavigateToHome) {
                Text("Go back to Home detail")
            }
        }
        var count by remember {
            mutableStateOf(0)
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
            Button(onClick = {
                count++
            }){
                Text("Count: $count")
            }
        }
    }

}