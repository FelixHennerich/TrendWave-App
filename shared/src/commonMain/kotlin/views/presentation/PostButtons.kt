package views.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent

/**
 * three buttons on home screen
 *
 * @param modifier -> Edits for HomeScreen
 * @param backgroundcolor -> Background
 * @param textcolor -> Color of text
 * @param onEvent -> EventHandling
 * @param event -> Type of event
 */
@Composable
fun PostButton(
    modifier: Modifier,
    backgroundcolor: Color,
    textcolor: Color,
    imageVector: ImageVector,
    onEvent: (TrendWaveEvent)-> Unit,
    event: TrendWaveEvent,
    notclickable: Boolean,
) {
    Column(
        modifier = modifier
            .height(85.dp)
            .width(85.dp)
            .border(
                width = 1.dp, color = textcolor, shape = RoundedCornerShape(
                    topStart = 90.dp,
                    topEnd = 90.dp,
                    bottomEnd = 90.dp,
                    bottomStart = 90.dp
                )
            )
            .background(
                backgroundcolor,
                RoundedCornerShape(
                    topStart = 90.dp,
                    topEnd = 90.dp,
                    bottomEnd = 90.dp,
                    bottomStart = 90.dp
                )
            ).clickable {
                if(!notclickable) {
                    onEvent(event)
                }

            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //Image inside the button
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.scale(1.4f),
            tint = textcolor
        )
    }
}
