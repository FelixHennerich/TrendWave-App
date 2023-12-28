package views.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import event.TrendWaveEvent
import utilities.color.Colors
import utilities.color.fromEnum
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


data class PostButtons(
    val modifier: Modifier,
    val backgroundcolor: Color,
    val textcolor: Color,
    val imageVector: ImageVector?,
    val text: String?,
    val onEvent: (TrendWaveEvent)-> Unit,
    val event: TrendWaveEvent,
    val notclickable: Boolean,
    val smallicon: ImageVector,
    val primarybackground: Color,
    val addbutton: Boolean,
    val id: String
)
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
    imageVector: ImageVector?,
    text: String?,
    onEvent: (TrendWaveEvent)-> Unit,
    event: TrendWaveEvent,
    notclickable: Boolean,
    smallicon: ImageVector,
    primarybackground: Color,
    addbutton: Boolean
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
    ) {
        Box(
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
                    if (!notclickable) {
                        onEvent(event)
                    }

                },
            contentAlignment = Alignment.Center,
        ) {
            //Image inside the button
            if(imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "",
                    modifier = Modifier.scale(1.4f),
                    tint = textcolor
                )
            }else if(text != null){
                var lst = emptyList<String>().toMutableList()
                if(text.contains(" ")){
                    lst = text.split(" ").toMutableList()
                }else {
                    lst += text
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column() {
                        for (entry in lst) {
                            Text(
                                text = entry,
                                color = textcolor,
                            )
                        }
                    }
                }
            }
        }
        var iconcolor by remember { mutableStateOf(textcolor) }
        if(addbutton)
            iconcolor = Color.fromEnum(Colors.GREEN)
        Icon(
            imageVector = smallicon,
            contentDescription = "",
            modifier = Modifier.padding(end = 10.dp)
                .border(width = 4.dp, color = primarybackground, shape = RoundedCornerShape(90))
                .background(color = primarybackground, shape = RoundedCornerShape(90)).scale(.8f),
            tint = iconcolor
        )
    }
}
