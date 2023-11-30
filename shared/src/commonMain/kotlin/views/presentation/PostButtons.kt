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
 * @param buttontext -> Text inside the button
 * @param onEvent -> EventHandling
 * @param event -> Type of event
 */
@Composable
fun PostButton(
    modifier: Modifier,
    buttontext: String,
    imageVector: ImageVector,
    onEvent: (TrendWaveEvent)-> Unit,
    event: TrendWaveEvent
) {
    Column(
        modifier = modifier
            .height(60.dp)
            .width(60.dp)
            .background(
                Color(230, 255, 255),
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            ).clickable {
                onEvent(event)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //Image inside the button
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.scale(1.1f).padding(top = 15.dp)
        )

        //Text below the button
        Text(
            text = buttontext,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.offset(y = 30.dp)
        )
    }
}
