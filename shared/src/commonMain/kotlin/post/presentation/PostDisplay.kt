package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.LineaIcons
import compose.icons.SimpleIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.User
import compose.icons.tablericons.Trash
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.Post
import post.PostLoader

/**
 * Display lingle posts
 *
 * @param modifier -> Modifications that will be available
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
            .background(Color(242, 242, 242), RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 10.dp,
                bottomEnd = 10.dp
            ))
    ) {
        Row(
            modifier = Modifier.offset(x = 8.dp, y = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(Color(255, 204, 204), RoundedCornerShape(30.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "",
                    modifier = Modifier.scale(1.3f),
                )
            }
            Text(
                text = "@$postuser",
                modifier = Modifier.offset(x = 6.dp, y = -(8).dp),
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            if(localDataStorageManager.readString("username") == postuser ||
                localDataStorageManager.readString("role") == "Admin"){
                IconButton(
                    onClick = {
                        GlobalScope.launch {
                            val postloader = PostLoader()
                            postloader.deletePost(postid)

                            onEvent(TrendWaveEvent.PostDeletionButton(Post(postid, postuuid, postuser, postdate, posttext), state.posts))
                        }
                    },
                    modifier = Modifier.offset(y = -(3).dp, x = 200.dp)
                ){
                    Icon(
                        imageVector = TablerIcons.Trash,
                        contentDescription = "",
                    )
                }
            }
        }
        Text(
            text = "Posted: $postdate",
            modifier = Modifier.offset(y = -(15).dp, x = 55.dp),
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 8.sp
        )
        Text(
            text = posttext,
            modifier = Modifier.padding(start = 50.dp, end = 10.dp, bottom = 20.dp)
        )
    }
}