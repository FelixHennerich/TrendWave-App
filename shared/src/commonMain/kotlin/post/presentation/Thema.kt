package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp

enum class Thema(val displayName: String) {
    THEMA1("Thema 1"),
    THEMA2("Thema 2"),
    THEMA3("Thema 3"),
}

@Composable
fun DropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedThema by remember { mutableStateOf(Thema.THEMA1) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Gray)
            .clickable { expanded = !expanded }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            BasicTextField(
                value = selectedThema.displayName,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.Refresh else Icons.Default.Add,
                contentDescription = null
            )
        }

        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .background(Color.White)
            ) {
                Column {
                    Thema.values().forEach { theme ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    selectedThema = theme
                                    expanded = false
                                }
                        ) {
                            Text(theme.displayName)
                        }
                    }
                }
            }
        }
    }
}
