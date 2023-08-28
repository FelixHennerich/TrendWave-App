package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextInputForTests
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.http.ContentType
import org.jetbrains.compose.resources.painterResource

class LoginScreen {


    @Composable
    fun LoginScreen() {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Icon()
            TextInput(InputType.Name)
            TextInput(InputType.Password)
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("SIGN IN", Modifier.padding(vertical = 8.dp))
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an Account?", color = Color.White)
                TextButton(onClick = {}) {
                    Text("SIGN UP")
                }
            }
        }
    }

    sealed class InputType(
        val label: String,
        val icon: ImageVector,
        val keyboardOptions: KeyboardOptions,
        val visualTransformation: VisualTransformation
    ) {
        object Name : InputType(
            label = "Username or E-mail",
            icon = Icons.Default.Person,
            KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = VisualTransformation.None
        )

        object Password : InputType(
            label = "Password",
            icon = Icons.Default.Lock,
            KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = VisualTransformation.None
        )
    }

    @Composable
    fun TextInput(inputType: InputType) {

        var value by remember { mutableStateOf("") }

        TextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = { Icon(imageVector = inputType.icon, null) },
            label = { Text(text = inputType.label) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.White
            ),
            singleLine = true,
            keyboardOptions = inputType.keyboardOptions,
            visualTransformation = inputType.visualTransformation
        )
    }
}