package views

import account.AccountManager
import account.UUID
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineaIcons
import compose.icons.lineaicons.Music
import compose.icons.lineaicons.music.Bell
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.launch
import utils.HTTPManager
import utils.MonthCalc


class SettingsView {

    /*
    ? Image Example:

            KamelImage(asyncPainterResource("https://github.com/FelixHennerich/DiscordWebhook/blob/main/Bildschirm%C2%ADfoto%202023-08-05%20um%2012.01.40.png?raw=true"),
                null
            )

     */

    private val url = "https://cross-cultural-auto.000webhostapp.com/php/connectUpdate.php";

    @Composable
    fun SettingsScreen(onNavigateToHome: () -> Unit) {
        Box(Modifier.offset(y = 10.dp).fillMaxSize(), contentAlignment = Alignment.TopStart) {
            IconButton(onClick = onNavigateToHome, Modifier.offset(x = 0.dp, y = 0.dp)) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
            }
            Text(
                "Settings",
                style = TextStyle(
                    Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp
                ), modifier = Modifier.offset(x = 15.dp, y = 55.dp)
            )

            Box(Modifier.offset(x = 20.dp, y = 130.dp)) {
                Icon(
                    imageVector = Icons.Rounded.AccountBox,
                    contentDescription = "",
                    Modifier.scale(1.3f)
                )
                Text(
                    "Account",
                    Modifier.offset(x = 40.dp, y = (-3).dp),
                    style = TextStyle(
                        Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )
                Icon(
                    imageVector = LineaIcons.Music.Bell,
                    contentDescription = "",
                    Modifier.width(100.dp).height(100.dp).offset(y = 100.dp)
                )

                /*val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        text = HTTPManager().postUpdate("https://cross-cultural-auto.000webhostapp.com/php/connectUpdate.php", "newsapplication", "b", "100", "test", "1").toString()
                    }
                }
                Text(text)*/



                val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        val accountManager = AccountManager()
                        text = accountManager.createAccount("fehennerich@outlook.de", "12345534", "felixhennerich", "01.04.2005","authcode1234jfj")
                            .toString()
                    }
                }
                Text(text, modifier = Modifier.offset(x = 50.dp, y= 300.dp))


            }
        }
    }
}