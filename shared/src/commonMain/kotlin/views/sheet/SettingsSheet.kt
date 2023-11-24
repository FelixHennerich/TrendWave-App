package views.sheet

import account.image.ImageDataSource
import account.manager.LogoutManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import event.TrendWaveState
import managers.DataStorageManager
import utilities.presentation.SideSheet
import views.LoginScreen

/**
 * Settings UI Screen
 *
 * @param isOpen -> Is Settings Shown
 * @param state -> Statemanager
 * @param localDataSource -> Datasource to display data
 * @param onEvent -> EventHandeling
 * @param onLogout -> Navigate to login screen on logout
 */
@Composable
fun SettingsSheet(
    isOpen: Boolean,
    state: TrendWaveState,
    localDataSource: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    onLogout: () -> Unit
) {

    // var darkMode by mutableStateOf(true)

    SideSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.White
    ) {
        Box(
            Modifier.offset(x = (-10).dp, y = 10.dp).fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(
                onClick = { onEvent(TrendWaveEvent.ClickCloseSettingsScreen) },
                Modifier.offset(x = 0.dp, y = 0.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    Modifier.padding(top = 20.dp)
                )

            }
            /*Row(Modifier.offset(x = 300.dp, y = 10.dp)) {
                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = !darkMode },
                    colors = SwitchDefaults.colors(Color.Black)
                )
            }*/


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
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp, end = 20.dp)
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }, modifier = Modifier.padding(top = 70.dp)) {
                        Text("Edit Account", color = Color.Gray, fontSize = 15.sp)
                    }
                    IconButton(
                        onClick = { },
                        Modifier.offset(x = 310.dp, y = 0.dp).padding(top = 61.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            Modifier.padding(top = 20.dp)
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }, modifier = Modifier.padding(top = 110.dp)) {
                        Text("Change Password", color = Color.Gray, fontSize = 15.sp)
                    }
                    IconButton(
                        onClick = { },
                        Modifier.offset(x = 310.dp, y = 0.dp).padding(top = 101.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            Modifier.padding(top = 20.dp)
                        )
                    }
                }


                /**
                 * SAMPLE
                 */
                Box(modifier = Modifier.fillMaxWidth().clickable {  }) {
                    Text(
                        text = "Change Status",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 150.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = "",
                        Modifier.offset(x = 310.dp, y = 0.dp).padding(top = 161.dp)
                    )
                }




                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }, modifier = Modifier.padding(top = 190.dp)) {
                        Text("Privacy", color = Color.Gray, fontSize = 15.sp)
                    }
                    IconButton(
                        onClick = { },
                        Modifier.offset(x = 310.dp, y = 0.dp).padding(top = 181.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            Modifier.padding(top = 20.dp)
                        )
                    }
                }
            }

            Box(Modifier.offset(x = 20.dp, y = 370.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Face,
                    contentDescription = "",
                    Modifier.scale(1.3f).padding(top = 20.dp)
                )
                Text(
                    "Profile",
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
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp, end = 20.dp)
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }, modifier = Modifier.padding(top = 70.dp)) {
                        Text("Your Profile", color = Color.Gray, fontSize = 15.sp)
                    }
                    IconButton(
                        onClick = { },
                        Modifier.offset(x = 310.dp, y = 0.dp).padding(top = 61.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            Modifier.padding(top = 20.dp)
                        )
                    }
                }
            }

            Box(Modifier.offset(x = 20.dp, y = 480.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    Modifier.scale(1.3f).padding(top = 20.dp)
                )
                Text(
                    "FAQ",
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
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp, end = 20.dp)
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }, modifier = Modifier.padding(top = 70.dp)) {
                        Text(
                            "Kein Plan was du hier von mir willst",
                            color = Color.Gray,
                            fontSize = 15.sp
                        )
                    }

                    IconButton(
                        onClick = { },
                        Modifier.offset(x = 310.dp, y = 0.dp).padding(top = 61.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            Modifier.padding(top = 20.dp)
                        )
                    }
                }

            }


            /**
             * LOGOUT
             */


            Box(modifier = Modifier
                .fillMaxWidth()
                .offset(x = 130.dp, y = 600.dp)
                .padding(50.dp)
                .clickable {
                    val logoutManager = LogoutManager(
                        state = state,
                        localDataManager = localDataSource
                    )
                    logoutManager.logout()
                    onLogout()
                })
            {
                    Row() {
                        Text(
                            text = "LOGOUT",
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            //modifier = Modifier.padding(top = 150.dp)
                        )
                        Icon(
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = "",
                            Modifier.offset(x = (-80).dp)
                        )
                    }
            }


        }
    }
}