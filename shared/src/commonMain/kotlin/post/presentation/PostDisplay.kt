package post.presentation

import account.AppUser
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.Trash
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.RESTfulPostManager
import post.Post

/**
 * Display lingle posts
 *
 * @param modifier -> Modifications that will be available
 * @param backgroundcolor -> Backgroundcolor
 * @param textcolor -> Color of text
 * @param iconbackgroundcolor -> Color if no pricture is there
 * @param posttext -> content of post
 * @param postuser -> Username of creator
 * @param postdate -> Date of creation
 * @param postuuid -> UUID of creator
 * @param postid -> ID of Post
 * @param localDataStorageManager -> Local Storage manager
 * @param onEvent -> Event handling
 * @param state -> State & Data manager
 */
@Composable
fun PostDisplay(
    modifier: Modifier,
    backgroundcolor: Color,
    textcolor: Color,
    iconbackgroundcolor: Color,
    posttext: String,
    postuser: String,
    postdate: String,
    postuuid: String,
    postid: String,
    localDataStorageManager: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .background(backgroundcolor, RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 10.dp,
                bottomEnd = 10.dp
            ))
    ) {
        Row(
            modifier = Modifier.offset(x = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(iconbackgroundcolor, RoundedCornerShape(30.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "",
                    tint = textcolor,
                    modifier = Modifier.scale(1.3f),
                )
            }
            TextButton(
                onClick = {
                    GlobalScope.launch {
                        val user = AppUser()
                        onEvent(
                            TrendWaveEvent.ClickUserProfileViewButton(
                                user.getUserByUsername(
                                    postuser
                                )
                            )
                        )
                    }
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().offset(y = -(10).dp, x = -(4).dp),
                ) {
                        Text(
                                text = "@$postuser",
                                color = textcolor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "Posted: $postdate",
                            modifier = Modifier.offset(y = 14.dp, x = -(157).dp),
                            color = textcolor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 8.sp
                        )
                        IconButton(
                            onClick = {
                                if (localDataStorageManager.readString("username") == postuser ||
                                    localDataStorageManager.readString("role") == "Admin"
                                ) {
                                    GlobalScope.launch {
                                        val restAPI = RESTfulPostManager(state)
                                        restAPI.deletePost(postid)

                                        onEvent(
                                            TrendWaveEvent.PostDeletionButton(
                                                Post(
                                                    postid,
                                                    postuuid,
                                                    postuser,
                                                    postdate,
                                                    posttext
                                                ), state.posts
                                            )
                                        )
                                    }
                                }
                            },
                        ) {
                            if (localDataStorageManager.readString("username") == postuser ||
                                localDataStorageManager.readString("role") == "Admin"
                            ) {
                                Icon(
                                    imageVector = TablerIcons.Trash,
                                    contentDescription = "",
                                    tint = textcolor
                                )
                            }else {
                                Icon(
                                    imageVector = TablerIcons.Trash,
                                    contentDescription = "",
                                    tint = backgroundcolor
                                )
                            }
                        }
                }
            }

        }
        Text(
            text = posttext,
            color = textcolor,
            modifier = Modifier.padding(start = 50.dp, end = 10.dp, bottom = 20.dp)
        )

    }
}