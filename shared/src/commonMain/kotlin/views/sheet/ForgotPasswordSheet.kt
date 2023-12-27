package views.sheet

import account.AppUser
import account.RESTfulUserManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalFocusManager
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
import utilities.CommonLogger
import utilities.ForgotPasswordAPI
import utilities.color.Colors
import utilities.color.fromEnum

/**
 * Screen to edit the email if u forgot your password
 *
 * @param onEvent -> EventHandling
 * @param corner -> Corner shape
 */
@Composable
fun ForgotPasswordSheet(
    onEvent: (TrendWaveEvent) -> Unit,
    corner: RoundedCornerShape
) {
    var uuid = mutableStateOf("")
    val focusManager = LocalFocusManager.current

    Box(
        Modifier.padding(10.dp).fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "",
            modifier = Modifier.padding(top = 20.dp).clickable {
                onEvent(TrendWaveEvent.ClickCloseForgotPasswordSheet)
            },
            tint = Color.fromEnum(Colors.SENARY)
        )
    }

    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.offset(y = 100.dp),
        contentAlignment = Alignment.Center,
    ) {
        TextField(
            value = email,
            placeholder = {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "E-mail",
                        modifier = Modifier.offset(y = -(2).dp, x = 3.dp),
                        color = Color.fromEnum(Colors.SENARY)
                    )
                }
            },
            onValueChange = { text -> email = text },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                .background(
                    color = Color.fromEnum(Colors.TERTIARY),
                    shape = corner
                )
                .border(
                    width = 1.dp,
                    color = Color.fromEnum(Colors.SENARY),
                    shape = corner
                ),
            textStyle = TextStyle(
                textAlign = TextAlign.Left,
                color = Color.fromEnum(Colors.SENARY),
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(
                onDone = { createAuthcode(email) },
            )
        )
    }

    Box(
        modifier = Modifier.offset(y = 200.dp)
    ){
        Button(
            onClick = {
                focusManager.clearFocus()
                createAuthcode(email)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.fromEnum(Colors.QUATERNARY)),
            shape = corner
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
fun createAuthcode(email: String){
    val api = ForgotPasswordAPI()
    GlobalScope.launch {
        val userclass = AppUser()
        val uuid = userclass.getUUID(email)

        val commonLogger = CommonLogger();
        commonLogger.log("were here$uuid")

        val code = api.createAuthcode(uuid)

        commonLogger.log(code)

        commonLogger.log(api.sendMail(code, email))
    }
}