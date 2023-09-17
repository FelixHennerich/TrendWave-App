package views.sheet

import account.image.ImageDataSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import managers.DataStorageManager
import utilities.presentation.SideSheet

/**
 * Settings UI Screen
 *
 * @param isOpen -> Is Settings Shown
 * @param imageDataSource -> Datasource to display images
 * @param localDataSource -> Datasource to display data
 * @param onEvent -> EventHandeling
 */
@Composable
fun SettingsSheet(
    isOpen: Boolean,
    imageDataSource: ImageDataSource,
    localDataSource: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit
) {
    SideSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.White
    ) {
        Box(Modifier.offset(y = 10.dp).fillMaxSize(), contentAlignment = Alignment.TopStart) {
            IconButton(onClick = {onEvent(TrendWaveEvent.ClickCloseSettingsScreen)}, Modifier.offset(x = 0.dp, y = 0.dp)) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    Modifier.padding(top = 20.dp)
                )
            }
            Text(
                "Settings",
                style = TextStyle(
                    Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp
                ), modifier = Modifier.offset(x = 15.dp, y = 55.dp).padding(top = 20.dp)
            )

            Box(Modifier.offset(x = 20.dp, y = 130.dp)) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "",
                    Modifier.scale(1.3f).padding(top = 20.dp)
                )
                Text(
                    "Account",
                    Modifier.offset(x = 40.dp, y = (-3).dp).padding(top = 20.dp),
                    style = TextStyle(
                        Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )

                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp)
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }, modifier = Modifier.padding(top = 70.dp)) {
                        Text("Edit Profile", color = Color.Gray, fontSize = 15.sp)
                    }
                    IconButton(onClick = {  }, Modifier.offset(x = 330.dp, y = 0.dp).padding(top = 61.dp)) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            Modifier.padding(top = 20.dp)
                        )
                    }
                }

            }
        }
    }
}