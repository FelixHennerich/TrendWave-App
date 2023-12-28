package post.presentation

import account.AppUser
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
    posttheme: String,
    postid: String,
    localDataStorageManager: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState,
    notclickable: Boolean,
) {
    Column(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .background(backgroundcolor, RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 10.dp,
                bottomEnd = 10.dp
            ))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Icon / Profile img
            Box(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 5.dp)
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

            //Text
            Box(
                modifier = Modifier.fillMaxHeight().offset(y = -(5).dp).clickable {
                    if(!notclickable) {
                        GlobalScope.launch {
                            onEvent(
                                TrendWaveEvent.ClickUserProfileViewButton(
                                    AppUser().getUserByUsername(
                                        postuser
                                    )
                                )
                            )
                        }
                    }
                },
            ) {
                Text(
                    text = "@$postuser",
                    color = textcolor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier
                )
                Text(
                    text = "Posted: $postdate",
                    color = textcolor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }

            //Trashcan
            Box(
                modifier = Modifier.fillMaxWidth().offset(y = -(5).dp).padding(end = 10.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    onClick = {
                        if (localDataStorageManager.readString("username") == postuser ||
                            localDataStorageManager.readString("role") == "Admin"
                        ) {
                            if(!notclickable) {
                                GlobalScope.launch {
                                    RESTfulPostManager().deletePost(postid)
                                    onEvent(
                                        TrendWaveEvent.PostDeletionButton(
                                            Post(postid, postuuid, postuser, postdate, posttext, posttheme),
                                            state.posts
                                        )
                                    )
                                }
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
                    } else {
                        Icon(
                            imageVector = TablerIcons.Trash,
                            contentDescription = "",
                            tint = backgroundcolor
                        )
                    }
                }
            }
        }
        Text(
            text = posttext,
            modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 10.dp).clickable {
                onEvent(TrendWaveEvent.ClickPostMessageDisplay(postuser, posttext, postdate, postid))
            },
            color = textcolor,
        )

    }
}