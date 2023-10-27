package views

import account.AppUser
import account.image.ImageDataSource
import account.image.Photo
import account.manager.LoginManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import managers.DataStorageOnLogin
import managers.exceptions.ExceptionHandler
import managers.exceptions.NException
import org.jetbrains.compose.resources.ExperimentalResourceApi
import utilities.presentation.SideSheet
import views.sheet.ForgotPasswordSheet


class LoginScreen {

    /**
     * Login screen for the app
     *
     * @param state -> StateManager
     * @param onEvent -> EventManager
     * @param onNavigateRegister -> Navigate to Register  screen
     * @param onNavigateHome -> Navigate to Home  screen
     * @param imageDataSource -> ImageAPI
     * @param localDataManager -> Use the local data managment
     */
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun LoginScreen(
        state: TrendWaveState,
        onEvent: (TrendWaveEvent) -> Unit,
        onNavigateRegister: () -> Unit,
        onNavigateHome: () -> Unit,
        imageDataSource: ImageDataSource,
        localDataManager: DataStorageManager
    ) {
        var user by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }


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
                    width = 250.dp,
                    height = 250.dp,
                    photoBytes = imageBytes
                )
            }
            // -----------------


            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "LOGIN",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            // Textfield for the E-mail / Username
            TextField(
                value = user,
                placeholder = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "E-mail"
                        )
                    }
                },
                onValueChange = { text -> user = text },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        GlobalScope.launch {
                            val loginManager = LoginManager(state)
                            val exceptionHandler = ExceptionHandler()
                            val userClass = AppUser(state)
                            val message = exceptionHandler.fetchErrorMessage(
                                loginManager.login(
                                    email = user,
                                    password = password
                                )
                            )

                            onEvent(TrendWaveEvent.ChangeLoginErrorMessage(message))
                            if (message == exceptionHandler.fetchErrorMessage(NException.SUCCESS001)) {
                                val uuid = userClass.getUUID(user)
                                val username = userClass.getUsername(uuid)
                                val role = userClass.getRole(uuid)

                                val DataStorageOnLogin = DataStorageOnLogin(localDataManager)
                                DataStorageOnLogin.storeData(user, password, username, role, uuid)

                                onNavigateHome()
                            }
                        }
                    }
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
                ),
            )
            // Textfield for the password
            TextField(
                value = password,
                placeholder = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Password"
                        )
                    }
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
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        GlobalScope.launch {
                            val loginManager = LoginManager(state)
                            val exceptionHandler = ExceptionHandler()
                            val userClass = AppUser(state)
                            val message = exceptionHandler.fetchErrorMessage(
                                loginManager.login(
                                    email = user,
                                    password = password
                                )
                            )

                            onEvent(TrendWaveEvent.ChangeLoginErrorMessage(message))
                            if (message == exceptionHandler.fetchErrorMessage(NException.SUCCESS001)) {
                                val uuid = userClass.getUUID(user)
                                val username = userClass.getUsername(uuid)
                                val role = userClass.getRole(uuid)

                                val DataStorageOnLogin = DataStorageOnLogin(localDataManager)
                                DataStorageOnLogin.storeData(user, password, username, role, uuid)

                                onNavigateHome()
                            }
                        }
                    }
                )
            )
            Text(text = state.LoginErrorMessage ?: "", color = Color.Red)
            TextButton(onClick = {
                onEvent(TrendWaveEvent.ClickForgotPasswordSheet)
            }) {
                Text(text = "Forgot your password?", color = Color.Blue)
            }

            // Button and other UI elements
            Button(
                onClick = {
                    GlobalScope.launch {
                        val loginManager = LoginManager(state)
                        val exceptionHandler = ExceptionHandler()
                        val userClass = AppUser(state)
                        val message = exceptionHandler.fetchErrorMessage(
                            loginManager.login(
                                email = user,
                                password = password
                            )
                        )

                        onEvent(TrendWaveEvent.ChangeLoginErrorMessage(message))
                        if (message == exceptionHandler.fetchErrorMessage(NException.SUCCESS001)) {
                            val uuid = userClass.getUUID(user)
                            val username = userClass.getUsername(uuid)
                            val role = userClass.getRole(uuid)

                            val DataStorageOnLogin = DataStorageOnLogin(localDataManager)
                            DataStorageOnLogin.storeData(user, password, username, role, uuid)

                            onNavigateHome()
                        }
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
                    text = "Login",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(20.dp))
            TextButton(onClick = {
                onNavigateRegister()
            }) {
                Text(
                    text = "Don't have an Account yet?",
                    Modifier.padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
        }
        SideSheet(
            visible = state.isForgetPasswordSheetOpen,
            modifier = Modifier.fillMaxSize(),
            backgroundcolor = Color.White,
        ){
            ForgotPasswordSheet(
                onEvent = onEvent,
                state = state
            )
        }
    }
}
