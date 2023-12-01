package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import event.TrendWaveState
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.RESTfulPostManager
import utilities.color.Colors
import utilities.color.fromEnum
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
    onEvent: (TrendWaveEvent) -> Unit,
    localDataSource: DataStorageManager,
    state: TrendWaveState
) {
    BottomSheet(
        visible = isOpen,
        modifier = Modifier.clip(RoundedCornerShape(
            topEnd = 30.dp,
            topStart = 30.dp
        )),
        backgroundcolor = Color.fromEnum(Colors.PRIMARY),
        padding = 0.dp
    ) {

        Scaffold(
            modifier = Modifier.offset(y = 25.dp).fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp).height(80.dp)
                    .fillMaxWidth().background(
                        Color.fromEnum(Colors.QUATERNARY),
                        RoundedCornerShape(
                            topStart = 13.dp,
                            topEnd = 13.dp,
                            bottomEnd = 13.dp,
                            bottomStart = 13.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            onEvent(TrendWaveEvent.ClickClosePostButton)
                        }
                    )
                    Text(
                        text = "Create new post",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }



            Column (
                modifier = Modifier.offset(y = 120.dp)
            ){
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
                        .border(1.dp, color = Color.DarkGray, shape = RoundedCornerShape(5)),
                    shape = RoundedCornerShape(50),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Left,
                        color = Color.DarkGray,
                        fontSize = 14.sp
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                var lastClickTime by remember { mutableStateOf(0L) }
                val delayMillis = 10000L

                Box(
                    modifier = Modifier.fillMaxWidth().offset(y = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    state.createPostErrorMessage?.let { it1 ->
                        Text(
                            text = it1,
                            color = Color.Red
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().offset(y = 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (post.length > 3) {
                                val currentTime = getTimeMillis()
                                if (currentTime - lastClickTime >= delayMillis) {
                                    onEvent(TrendWaveEvent.ClickClosePostButton)

                                    GlobalScope.launch {
                                        val restapi = RESTfulPostManager(state)
                                        localDataSource.readString("uuid")?.let {
                                            val post = restapi.uploadPost(
                                                uuid = it,
                                                text = post
                                            )
                                            onEvent(TrendWaveEvent.LocalPostCreation(post))
                                        }
                                    }
                                    lastClickTime = currentTime
                                }
                            }else {
                                onEvent(TrendWaveEvent.ChangePostErrorMessage("Your post is too short"))
                            }
                        },
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}