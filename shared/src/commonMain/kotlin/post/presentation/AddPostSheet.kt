package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.Settings
import event.TrendWaveEvent
import utilities.presentation.BottomSheet

/**
 * AddPostsheet for post adding
 *
 * @param isOpen -> Sheet shown or not
 * @param onEvent -> EventHandling
 */
@Composable
fun addPostSheet(
    isOpen: Boolean,
    onEvent: (TrendWaveEvent) -> Unit
) {
    BottomSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(
            topEnd = 30.dp,
            topStart = 30.dp
        )),
        backgroundcolor = Color.White
    ) {

        var post by remember { mutableStateOf("") }

        TextField(
            value = post,
            placeholder = {
                Text(
                    text = "Enter your post",
                    modifier = Modifier.offset(y = (-3).dp),
                )
            },
            onValueChange = { text -> post = text },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 30.dp, end = 30.dp, top = 8.dp, bottom = 8.dp)
                .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(10)),
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

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onEvent(TrendWaveEvent.ClickClosePostButton) },
            ) {
                Text("Submit")
            }
        }
    }
}