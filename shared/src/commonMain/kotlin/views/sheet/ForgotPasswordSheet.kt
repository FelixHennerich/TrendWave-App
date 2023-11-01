package views.sheet

import account.AppUser
import account.RESTfulUserManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import utilities.ForgotPasswordAPI

/**
 * Screen to edit the email if u forgot your password
 *
 * @param onEvent -> EventHandling
 */
@Composable
fun ForgotPasswordSheet(
    onEvent: (TrendWaveEvent) -> Unit,
    state: (TrendWaveState)
) {
    var uuid = mutableStateOf("")
    Box(
        Modifier.offset(x = (-10).dp, y = 10.dp).fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = { onEvent(TrendWaveEvent.ClickCloseForgotPasswordSheet) },
            Modifier.offset(x = 0.dp, y = 0.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "",
                Modifier.padding(top = 20.dp)
            )

        }
    }

    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.offset(y =200.dp),
        contentAlignment = Alignment.Center,
    ) {
        TextField(
            value = email,
            placeholder = {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "E-mail"
                    )
                }
            },
            onValueChange = { text -> email = text },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {

                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 50.dp, end = 50.dp, top = 8.dp, bottom = 8.dp)
                .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(30)),
            shape = RoundedCornerShape(30),
            textStyle = TextStyle(
                textAlign = TextAlign.Left,
                color = Color.Blue,
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }

    Box(
        modifier = Modifier.offset(y = 300.dp)
    ){
        Button(
            onClick = {
                val api = ForgotPasswordAPI()
                GlobalScope.launch {
                    val userclass = AppUser(state = state)
                    val uuid = userclass.getUUID(email)
                    api.createAuthcode(uuid)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 50.dp, end = 50.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            shape = RoundedCornerShape(30)
        ) {
            Text(
                text = "Send Request",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}