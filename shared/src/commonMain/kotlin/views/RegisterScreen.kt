package views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class RegisterScreen {


    /**
     * Register screen for creating new users
     *
     */
    @Composable
    fun RegisterScreen() {
        var user by remember { mutableStateOf("Username") }
        var email by remember { mutableStateOf("E-mail") }
        var password by remember { mutableStateOf("Password") }
        var birthday by remember { mutableStateOf("dd/mm/yyyy") }
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
            Text(text = "Logo") //Todo: Logo
            KamelImage(
                asyncPainterResource(
                    "",
                    Modifier.height(20.dp).width(20.dp)
                ),
                null
            )
            Spacer(Modifier.height(100.dp))
            Text(
                "REGISTER",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            TextField(
                value = email,
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
                    textAlign = TextAlign.Center,
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
                onValueChange = { text -> password = text },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                    .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
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
                    textAlign = TextAlign.Center,
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
                    textAlign = TextAlign.Center,
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Text(text = "error line", color = Color.Red) //TODO add error if there
            TextButton(onClick = {
                //Todo navigate to login screen
            }) {
                Text(
                    text = "already have an account? Login",
                    Modifier.padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
            Button(
                onClick = {
                          //Todo creationmanager handles over here
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
                    modifier = Modifier.padding(5.dp),
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
