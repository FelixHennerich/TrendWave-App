package views.sheet

import account.manager.LogoutManager
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Settings
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
import event.TrendWaveState
import managers.DataStorageManager
import utilities.presentation.SideSheet

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
    //Sidesheet is the sliding animation
    SideSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.White
    ) {
        Box(
            modifier = Modifier.offset(x = (-10).dp, y = 10.dp).fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {

            //Back button
            IconButton(
                onClick = { onEvent(TrendWaveEvent.ClickCloseSettingsScreen) },
                modifier = Modifier.offset(x = 0.dp, y = 0.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.padding(top = 20.dp)
                )

            }

            //Settings headline
            Text(
                text = "Settings",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp
                ), modifier = Modifier.offset(x = 15.dp, y = 55.dp).padding(top = 20.dp)
            )


            /**
             * Account section
             */

            Box(Modifier.offset(x = 20.dp, y = 130.dp)) {
                //Account Icon
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.scale(1.3f).padding(top = 20.dp)
                )

                //Account Text
                Text(
                    text = "Account",
                    modifier = Modifier.offset(x = 40.dp, y = (-3).dp).padding(top = 20.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )

                //Dividing Line
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp, end = 20.dp)
                )

                //All settings in Account
                Box(
                    modifier = Modifier.offset(y = 80.dp)
                ) {
                    //Change Username
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Change Username",
                            color = Color.Gray,
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp)
                        )
                    }

                    //Change Password
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(top = 40.dp).clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Change Password",
                            color = Color.Gray,
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp)
                        )
                    }


                    //Change Status
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(top = 80.dp).clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Change Status",
                            color = Color.Gray,
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp)
                        )
                    }

                    //Privacy
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(top = 120.dp).clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Privacy",
                            color = Color.Gray,
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp)
                        )
                    }
                }
            }


            /**
             * Other app Settings
             */
            Box(Modifier.offset(x = 20.dp, y = 380.dp)) {
                //Settings Icon
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "",
                    modifier = Modifier.scale(1.3f).padding(top = 20.dp)
                )

                //App Setting Text
                Text(
                    text = "You're App",
                    modifier = Modifier.offset(x = 40.dp, y = (-3).dp).padding(top = 20.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )

                //Dividing line
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp, end = 20.dp)
                )

                //TODO Add settings
            }


            /**
             * LOGOUT
             */


            Box(
                modifier = Modifier.fillMaxWidth().offset(x = 130.dp, y = 600.dp).padding(50.dp)
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
                    //Logout Text
                    Text(
                        text = "LOGOUT",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )

                    //Logout Icon
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