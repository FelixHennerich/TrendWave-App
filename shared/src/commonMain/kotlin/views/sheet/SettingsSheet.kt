package views.sheet

import account.manager.LogoutManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import utilities.color.Colors
import utilities.color.fromEnum
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
    onLogout: () -> Unit,
    corner: RoundedCornerShape
) {
    //Sidesheet is the sliding animation
    SideSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.fromEnum(Colors.PRIMARY)
    ) {
        //Bottom Bar
        Box(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp).height(80.dp)
                .fillMaxWidth().background(
                    color = Color.fromEnum(Colors.QUATERNARY),
                    shape = corner
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            //Create new post box
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.padding(10.dp).clickable {
                        onEvent(TrendWaveEvent.ClickCloseSettingsScreen)
                    },
                    tint = Color.fromEnum(Colors.SENARY)
                )
                Text(
                    text = "Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.fromEnum(Colors.SENARY),
                    modifier = Modifier.offset(y = 7.dp).padding(start = 15.dp)
                )
            }
        }


        Box(
            modifier = Modifier.padding(10.dp).offset(x = (-10).dp, y = 10.dp).fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {


            /**
             * Account section
             */

            Box(Modifier.offset(x = 20.dp)) {
                //Account Icon
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.scale(1.3f).padding(top = 20.dp),
                    tint = Color.fromEnum(Colors.SENARY)
                )

                //Account Text
                Text(
                    text = "Account",
                    modifier = Modifier.offset(x = 40.dp, y = (-3).dp).padding(top = 20.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    color = Color.fromEnum(Colors.SENARY)
                )

                //Dividing Line
                Divider(
                    color = Color.fromEnum(Colors.SENARY),
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
                            color = Color.fromEnum(Colors.SENARY),
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp),
                            tint = Color.fromEnum(Colors.SENARY)
                        )
                    }

                    //Change Password
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(top = 40.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Change Password",
                            color = Color.fromEnum(Colors.SENARY),
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp),
                            tint = Color.fromEnum(Colors.SENARY),
                        )
                    }


                    //Change Status
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(top = 80.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Change Status",
                            color = Color.fromEnum(Colors.SENARY),
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp),
                            tint = Color.fromEnum(Colors.SENARY),
                        )
                    }

                    //Privacy
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(top = 120.dp)
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Privacy",
                            color = Color.fromEnum(Colors.SENARY),
                            fontSize = 15.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 40.dp),
                            tint = Color.fromEnum(Colors.SENARY),
                        )
                    }
                }
            }


            /**
             * Other app Settings
             */
            Box(Modifier.padding(start = 20.dp, top = 260.dp)) {
                //Settings Icon
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "",
                    modifier = Modifier.scale(1.3f).padding(top = 20.dp),
                    tint = Color.fromEnum(Colors.SENARY),
                )

                //App Setting Text
                Text(
                    text = "You're App",
                    modifier = Modifier.offset(x = 40.dp, y = (-3).dp).padding(top = 20.dp),
                    style = TextStyle(
                        color = Color.fromEnum(Colors.SENARY),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )

                //Dividing line
                Divider(
                    color = Color.fromEnum(Colors.SENARY),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(top = 70.dp, end = 20.dp)
                )

                //TODO Add settings
            }


            /**
             * LOGOUT
             */


            Box(
                modifier = Modifier.fillMaxWidth().padding(start = 50.dp,top = 500.dp)
                    .clickable {
                        //Logout function called
                        val logoutManager = LogoutManager(
                            state = state,
                            localDataManager = localDataSource,
                            onEvent = onEvent
                        )
                        logoutManager.logout()
                        onLogout()
                    },
                contentAlignment = Alignment.Center
            ) {
                Row() {
                    //Logout Text
                    Text(
                        text = "LOGOUT",
                        color = Color.fromEnum(Colors.SENARY),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )

                    //Logout Icon
                    Icon(
                        imageVector = Icons.Rounded.ExitToApp,
                        contentDescription = "",
                        modifier = Modifier.offset(x = (-100).dp),
                        tint = Color.fromEnum(Colors.SENARY),
                    )
                }
            }
        }
    }
}