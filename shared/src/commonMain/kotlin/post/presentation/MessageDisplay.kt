package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import utilities.color.Colors
import utilities.color.fromEnum
import utilities.presentation.BottomSheet

@Composable
fun MessageDisplay(
    visible: Boolean,
    modifier: Modifier,
    backgroundcolor: Color,
    authorname: String,
    posttext: String,
    postdate: String,
    postuuid: String,
    onEvent: (TrendWaveEvent) -> Unit,
    corner: RoundedCornerShape
) {
    BottomSheet(
        visible = visible,
        modifier = modifier,
        padding = 0.dp,
        backgroundcolor = backgroundcolor

    ){
        Box(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 40.dp).height(80.dp)
                .fillMaxWidth().background(
                    color = Color.fromEnum(Colors.QUATERNARY),
                    shape = corner
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            //Create new post box
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = Color.fromEnum(Colors.SENARY),
                    modifier = Modifier.padding(10.dp).clickable {
                        onEvent(TrendWaveEvent.ClickCloseMessageDisplay)
                    }
                )
                Text(
                    text = "Author: $authorname",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.fromEnum(Colors.SENARY),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth().offset(y = 100.dp).background(Color.fromEnum(Colors.TERTIARY), corner).padding(20.dp),
            ){
                Text(
                    text = "Posted on: $postdate",
                    color = Color.fromEnum(Colors.SENARY),
                    fontSize = 15.sp
                )
                Text(
                    text = "$posttext",
                    color = Color.fromEnum(Colors.SENARY),
                    fontSize = 15.sp,
                    modifier = Modifier.offset(y = 50.dp)
                )
            }


        }



    }
}