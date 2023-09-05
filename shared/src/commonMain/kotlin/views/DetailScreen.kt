package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailScreen {

    /**
     * Login View
     */
    @Composable
    fun LoginScreen() {
        var value by remember { mutableStateOf("") }
        var username by remember{ mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val textFieldColors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )

        Column(
            modifier = Modifier
                .padding(vertical = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login Account",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )
            TextField(
                value = value,
                onValueChange = { value = it },
                colors = textFieldColors,
                modifier = Modifier.padding(vertical = 20.dp),
                label = { Text("E-Mail") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { println() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp)
            ){
                Text(text = "Login")
            }
        }

    }

}