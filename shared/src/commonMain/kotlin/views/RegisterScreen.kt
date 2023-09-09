package views

import account.image.ImageDataSource
import account.image.Photo
import account.manager.CreationManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import event.TrendWaveState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.exceptions.ExceptionHandler
import managers.exceptions.NException

class RegisterScreen {


    /**
     * Register screen for creating new users
     *
     * @param state -> StateManager
     * @param onEvent -> EventManager
     * @param onNavigateLogin -> Navigate to Login screen
     * @param onNavigateHome -> Navigate to Home screen
     * @param imageDataSource -> ImageAPI
     */
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun RegisterScreen(
        state: TrendWaveState,
        onEvent: (TrendWaveEvent) -> Unit,
        onNavigateLogin: () -> Unit,
        onNavigateHome: () -> Unit,
        imageDataSource: ImageDataSource
    ) {
        var user by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var birthday by remember { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val checkedConditionsState = remember {
            mutableStateOf(false)
        }

        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo of the APP
            var imageBytes by remember { mutableStateOf<ByteArray?>(null) }
            var loading by remember { mutableStateOf(true) }


            if (loading) {
                LaunchedEffect(loading) {
                    imageBytes = imageDataSource.getImage("LogoTransparent.jpg")
                    loading = false
                }
            }

            imageBytes?.let {
                Photo(
                    width = 150.dp,
                    height = 150.dp,
                    photoBytes = imageBytes
                )
            }
            // -----------------


            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "REGISTER",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            TextField(
                value = email,
                placeholder = {
                    Text(
                        text = "E-Mail",
                        modifier = Modifier.offset(y = (-3).dp),
                    )
                },
                onValueChange = { text -> email = text },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { email = "" }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                    .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                textStyle = TextStyle(
                    textAlign = TextAlign.Left,
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            // Textfield for the password
            TextField(
                value = password,
                placeholder = {
                    Text(
                        text = "Password",
                        modifier = Modifier.offset(y = (-3).dp),
                    )
                },
                onValueChange = { text -> password = text },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                    .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TextField(
                value = user,
                placeholder = {
                    Text(
                        text = "Username",
                        modifier = Modifier.offset(y = (-3).dp),
                    )
                },
                onValueChange = { text -> user = text },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { user = "" }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                    .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                textStyle = TextStyle(
                    textAlign = TextAlign.Left,
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = birthday,
                placeholder = {
                    Text(
                        text = "Birthday dd.mm.yyyy",
                        modifier = Modifier.offset(y = (-3).dp),
                    )
                },
                onValueChange = { text -> birthday = text },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { birthday = "" }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                    .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                textStyle = TextStyle(
                    textAlign = TextAlign.Left,
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Text(text = state.RegisterErrorMessage ?: "", color = Color.Red)
            TextButton(onClick = {
                onNavigateLogin()
            }) {
                Text(
                    text = "Already have an account? Login",
                    Modifier.padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
            Button(
                onClick = {
                    if(checkedConditionsState.value) {
                        GlobalScope.launch {
                            val creationManager = CreationManager()
                            val exceptionHandler = ExceptionHandler()
                            val message = exceptionHandler.fetchErrorMessage(
                                creationManager.createAccount(
                                    email,
                                    password,
                                    user,
                                    birthday
                                )
                            )

                            onEvent(TrendWaveEvent.ChangeRegisterErrorMessage(message))

                            if (message == exceptionHandler.fetchErrorMessage(NException.SUCCESS001)) {
                                onNavigateHome()
                            }
                        }
                    }else {
                        onEvent(TrendWaveEvent.ChangeRegisterErrorMessage("Check the terms of use below"))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 65.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Submit",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(100.dp))

        }



        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(700.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkedConditionsState.value,
                    onCheckedChange = { checkedConditionsState.value = it },
                    modifier = Modifier.padding(5.dp).offset(y = -(18).dp),
                    colors = CheckboxDefaults.colors(Color.Blue)
                )

                Text(
                    text = "I accept the terms of use",
                    modifier = Modifier
                        .fillMaxHeight(),
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
        }

    }

}
