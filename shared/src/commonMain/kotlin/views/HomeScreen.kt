package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.AllIcons
import compose.icons.FeatherIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.Settings
import compose.icons.tablericons.Settings
import compose.icons.tablericons.SettingsAutomation

class HomeScreen {

    /**
     * HOME SCREEN Screen after login or register
     *
     * @param onNavigateToSettings -> naviagte to Settings screen
     */
    @Composable
    fun HomeScreen(onNavigateToSettings: () -> Unit) {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    onClick = onNavigateToSettings
                ) {
                    Icon(
                        imageVector = TablerIcons.Settings,
                        contentDescription = "",
                        modifier = Modifier
                            .scale(1.3f)

                    )
                }
            }

            Text("For you")
        }
    }

}