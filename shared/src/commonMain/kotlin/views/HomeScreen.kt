package views

import account.image.ImageDataSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.Settings
import event.TrendWaveEvent
import event.TrendWaveState
import managers.DataStorageManager
import views.presentation.PostButton
import views.sheet.SettingsSheet
import views.sheet.addPostSheet

class HomeScreen {

    /**
     * HOME SCREEN Screen after login or register
     *
     * @param onEvent -> EventHandling in Viewmodel
     * @param state -> StateManager for PostAddSheet
     * @param localDataSource -> Get local data system
     * @param imageDataSource -> Get Image data system
     */
    @Composable
    fun HomeScreen(
        onEvent: (TrendWaveEvent) -> Unit,
        state: TrendWaveState,
        localDataSource: DataStorageManager,
        imageDataSource: ImageDataSource
    ) {
        Scaffold (
            Modifier.offset(y = 25.dp)
        ){
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(Color(230, 255, 255)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hello, ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 15.dp, start = 35.dp)
                )
                Text(
                    text = "${localDataSource.readString("username")}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 15.dp, end = 180.dp)
                )
                IconButton(
                    onClick = { onEvent(TrendWaveEvent.ClickSettingsScreen) },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Icon(
                        imageVector = TablerIcons.Settings,
                        contentDescription = "",
                        modifier = Modifier
                            .scale(1.5f)

                    )
                }
            }
            Row(
                modifier = Modifier.offset(y = 100.dp, x= 35.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                PostButton(
                    modifier = Modifier,
                    buttontext = "Post",
                    onEvent = onEvent,
                    event = TrendWaveEvent.ClickPostButton
                )
                PostButton(
                    modifier = Modifier,
                    buttontext = "Test",
                    onEvent = onEvent,
                    event = TrendWaveEvent.TestHomeButton
                )
                PostButton(
                    modifier = Modifier,
                    buttontext = "Test",
                    onEvent = onEvent,
                    event = TrendWaveEvent.TestHomeButton
                )
            }
        }
        addPostSheet(
            isOpen = state.isAddPostSheetOpen,
            onEvent = onEvent
        )
        SettingsSheet(
            isOpen = state.isSettingsSheetOpen,
            imageDataSource = imageDataSource,
            localDataSource = localDataSource,
            onEvent = onEvent
        )
    }

}