package account.presentation

import account.RESTfulUserManager
import account.manager.FollowManagerClass
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.Post
import post.RESTfulPostManager
import post.presentation.PostDisplay
import utilities.presentation.BottomSheet

@Composable
fun ProfileSheet(
    isOpen: Boolean,
    localDataSource: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState,
    pageOwner: RESTfulUserManager.User,
) {
    BottomSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.White,
        padding = 0.dp
    ) {
        var posts by remember { mutableStateOf<List<Post>>(emptyList()) }

        GlobalScope.launch {
            val restapi = RESTfulPostManager(state)
            posts = restapi.getUserPosts(pageOwner.uuid)
        }

        Scaffold(
            modifier = Modifier.offset(y = 25.dp).fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(Color(230, 255, 255)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {onEvent(TrendWaveEvent.ClickCloseProfileScreen)}) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "",
                    )
                }
                Text(
                    text = "@",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = pageOwner.username,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(x = 3.dp).weight(1f)
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth().offset(y = 90.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(255, 204, 204), RoundedCornerShape(30.dp))
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "",
                        modifier = Modifier.scale(3f),
                    )
                }
            }

            Row (
                modifier = Modifier.offset(y = 160.dp).fillMaxWidth().padding(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                Text(
                    text = "Follower: ${pageOwner.follower}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
                Text(
                    text = "Following: ${pageOwner.following}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }

            var color by remember { mutableStateOf(Color.Transparent) }
            if (state.user?.followed?.contains(pageOwner.uuid) == false) {
                color = Color.Red
            }else {
                color = Color.LightGray
            }

            Column(
                modifier = Modifier.fillMaxWidth().offset(y = 265.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(color, RoundedCornerShape(20))
                        .clickable {
                            GlobalScope.launch {
                                val followManager = FollowManagerClass(state, onEvent)
                                if (state.user?.followed?.contains(pageOwner.uuid) == false) {
                                    state.user?.let { it1 ->
                                        followManager.followUser(
                                            it1.uuid,
                                            pageOwner.uuid
                                        )
                                        onEvent(TrendWaveEvent.FollowUser(
                                            pageOwner.uuid,
                                            state.user!!
                                        ))
                                    }
                                    onEvent(TrendWaveEvent.ClickCloseProfileScreen)
                                }else {
                                    state.user?.let { it1 ->
                                        followManager.unfollowUser(
                                            it1.uuid,
                                            pageOwner.uuid
                                        )
                                        onEvent(TrendWaveEvent.UnfollowUser(
                                            pageOwner.uuid,
                                            state.user!!
                                        ))
                                    }
                                    onEvent(TrendWaveEvent.ClickCloseProfileScreen)

                                }
                            }
                        }
                ) {
                    if (state.user?.followed?.contains(pageOwner.uuid) == false) {
                        if (pageOwner.uuid != state.user?.uuid) {
                            Text(
                                text = "Subscribe",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }else {
                        if (pageOwner.uuid != state.user?.uuid) {
                            Text(
                                text = "Subscribed",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.offset(y = 320.dp)
            ) {
                Text(
                    text = "${pageOwner.username}'s activity",
                    modifier = Modifier.offset(x = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                for (post in posts) {
                    PostDisplay(
                        modifier = Modifier.fillMaxWidth(),
                        posttext = post.text,
                        postuser = post.username,
                        postuuid = post.uuid,
                        postdate = post.date,
                        postid = post.id,
                        localDataStorageManager = localDataSource,
                        onEvent = onEvent,
                        state = state
                    )
                }
            }
        }
    }
}