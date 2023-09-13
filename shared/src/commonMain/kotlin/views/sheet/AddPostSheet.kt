package views.sheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    ){
        Text("ADD POSTS HERE")

        Button(
            onClick = { onEvent(TrendWaveEvent.ClickClosePostButton) }
        ){
            Text("Submit")
        }
    }
}