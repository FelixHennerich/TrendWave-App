package views

import account.image.ImageDataSource
import account.image.Photo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineaIcons
import compose.icons.lineaicons.Music
import compose.icons.lineaicons.music.Bell
import kotlinx.coroutines.delay
import utilities.CommonLogger


class SettingsScreen {
    /**
     * Settings UI Screen
     *
     * @param onNavigateToHome -> Navigator left top
     * @param imageDataSource -> Datasource to display images
     */
    @Composable
    fun SettingsScreen(
        onNavigateToHome: () -> Unit,
        imageDataSource: ImageDataSource
    ) {

        Box(Modifier.offset(y = 10.dp).fillMaxSize(), contentAlignment = Alignment.TopStart) {
            IconButton(onClick = onNavigateToHome, Modifier.offset(x = 0.dp, y = 0.dp)) {
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

                /*val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        text = HTTPManager().postUpdate("https://cross-cultural-auto.000webhostapp.com/php/connectUpdate.php", "newsapplication", "b", "100", "test", "1").toString()
                    }
                }
                Text(text)*/


                /*
                val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        val accountManager = CreationManager()
                        val ExceptionHandler = ExceptionHandler()
                        val authcodemanager = AuthCodeManager()
                        val user = User();
                        //val code = authcodemanager.getNewAuthcode()
                        //text = authcodemanager.deactivateAuthcode(code)
                        text = "" + ExceptionHandler.fetchErrorMessage(accountManager.createAccount("nitroxblue1@gmail.com", "password123", "1BlueNitrox", "01.04.2005"))
                        //text = HTTPManager().usernameCheck("https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/checkUsername.php", "newsuser","felixhenneric1h", "authcode1234jfj"
                        //text = user.getEmail("0059fb5e1eff0fb8abccec0701ee38a7")
                    }
                }
                Text(text, modifier = Modifier.offset(x = 0.dp, y= 300.dp))
                */

            }
        }
    }
}

/*
    Image Example:
            KamelImage(asyncPainterResource("https://github.com/FelixHennerich/DiscordWebhook/blob/main/Bildschirm%C2%ADfoto%202023-08-05%20um%2012.01.40.png?raw=true"),
                null
            )

     */