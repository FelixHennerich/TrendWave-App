package post.presentation

import account.AppUser
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import compose.icons.FeatherIcons
import compose.icons.feathericons.Paperclip
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utilities.CommonLogger
import utilities.color.Colors
import utilities.color.fromEnum
import utilities.presentation.BottomSheet
import views.presentation.PostButton
import views.presentation.PostButtonManager

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MessageDisplay(
    visible: Boolean,
    modifier: Modifier,
    backgroundcolor: Color,
    authorname: String,
    posttext: String,
    postdate: String,
    postid: String,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState,
    corner: RoundedCornerShape,
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
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = FeatherIcons.Paperclip,
                        contentDescription = "",
                        tint = Color.fromEnum(Colors.SENARY),
                        modifier = Modifier.padding(end = 30.dp).clickable {
                            GlobalScope.launch {
                                //Is button already in homebuttonlist?
                                val add = !(PostButtonManager().isIDinHomeButtonList(state, postid))
                                PostButtonManager().buttonChange(
                                    add = add,
                                    type = 1,
                                    uuid = postid,
                                    user = state.user!!.uuid,
                                    onEvent = onEvent
                                )
                                delay(50)
                                onEvent(TrendWaveEvent.ClickCloseMessageDisplay)
                            }
                        }
                    )
                }
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