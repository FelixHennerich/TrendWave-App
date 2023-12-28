package account.presentation

import account.AppUser
import account.RESTfulUserManager
import account.manager.FollowManagerClass
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.Post
import post.RESTfulPostManager
import post.presentation.PostDisplay
import utilities.color.Colors
import utilities.color.fromEnum
import androidx.compose.foundation.lazy.items
import compose.icons.FeatherIcons
import compose.icons.feathericons.Paperclip
import kotlinx.coroutines.delay
import utilities.CommonLogger
import utilities.presentation.BottomSheet
import views.presentation.PostButtonManager

/**
 * ProfileSheet to display the profile of a player
 *
 * @param isOpen -> Is the sheet shown
 * @param localDataSource -> Handle local data
 * @param onEvent -> Manage the eventsystem
 * @param state -> Update cache data
 * @param pageOwner -> User of the profile sheet
 */
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ProfileSheet(
    isOpen: Boolean,
    localDataSource: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState,
    pageOwneruuid: String,
) {
    BottomSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.fromEnum(Colors.PRIMARY),
        padding = 0.dp
    ) {
        var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
        var pageOwner by remember { mutableStateOf(RESTfulUserManager.User("","","","","","","","","","","")) }
        GlobalScope.launch {
            pageOwner = AppUser().getUser(pageOwneruuid)
        }
        if(pageOwner.uuid != "") {
            GlobalScope.launch {
                val restapi = RESTfulPostManager()
                posts = restapi.getUserPosts(pageOwner.uuid)

            }

            Box(
                modifier = Modifier.padding(top = 20.dp).background(Color.fromEnum(Colors.PRIMARY))
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp).height(80.dp)
                        .fillMaxWidth().background(
                            Color.fromEnum(Colors.QUATERNARY),
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomEnd = 10.dp,
                                bottomStart = 10.dp
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "",
                            tint = Color.fromEnum(Colors.SENARY),
                            modifier = Modifier.padding(start = 10.dp).clickable {
                                GlobalScope.launch {
                                    delay(50)
                                    onEvent(TrendWaveEvent.ClickCloseProfileScreen)
                                }
                            }
                        )
                        Text(
                            text = "@",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.offset(y = -(3).dp).padding(start = 15.dp),
                            color = Color.fromEnum(Colors.SENARY)
                        )
                        Text(
                            text = pageOwner.username,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(y = -(3).dp).padding(start = 3.dp),
                            color = Color.fromEnum(Colors.SENARY)
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
                                        pinUser(onEvent, state, pageOwner)
                                    }
                                }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().offset(y = 110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .background(Color.fromEnum(Colors.QUINARY), RoundedCornerShape(30.dp))
                            .padding(50.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "",
                            modifier = Modifier.scale(3f),
                            tint = Color.fromEnum(Colors.SENARY)
                        )
                    }
                }

                Row(
                    modifier = Modifier.offset(y = 180.dp).fillMaxWidth().padding(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = "Follower: ${pageOwner.follower}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(15.dp),
                        color = Color.fromEnum(Colors.SENARY)
                    )
                    Text(
                        text = "Following: ${pageOwner.following}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(15.dp),
                        color = Color.fromEnum(Colors.SENARY)
                    )
                }

                var color by remember { mutableStateOf(Color.Transparent) }
                GlobalScope.launch {
                    val followManagerClass = FollowManagerClass()
                    if (!followManagerClass.isFollowing(state.user?.uuid!!, pageOwner.uuid))
                        color = Color.fromEnum(Colors.QUATERNARY)
                    else
                        color = Color.fromEnum(Colors.QUINARY)
                }

                Column(
                    modifier = Modifier.fillMaxWidth().offset(y = 285.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(color, RoundedCornerShape(20))
                    ) {
                        var text by remember { mutableStateOf("") }
                        GlobalScope.launch {
                            val followManagerClass = FollowManagerClass()
                            if (!followManagerClass.isFollowing(
                                    state.user?.uuid!!,
                                    pageOwner.uuid
                                )
                            ) {
                                text = "Follow"
                            } else {
                                text = "Followed"
                            }
                        }

                        if (pageOwner.uuid != state.user?.uuid) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = text,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(10.dp).clickable {
                                        followUser(onEvent, state, pageOwner)
                                    }
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 340.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    Text(
                        text = "${pageOwner.username}'s activity",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.fromEnum(Colors.SENARY)
                    )

                    for (post in posts) {
                        PostDisplay(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                            textcolor = Color.fromEnum(Colors.SENARY),
                            iconbackgroundcolor = Color.fromEnum(Colors.QUINARY),
                            posttext = post.text,
                            postuser = post.username,
                            postuuid = post.uuid,
                            postdate = post.date,
                            postid = post.id,
                            posttheme = post.theme,
                            localDataStorageManager = localDataSource,
                            onEvent = onEvent,
                            state = state,
                            notclickable = false,
                        )
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

fun followUser(onEvent: (TrendWaveEvent) -> Unit,
               state: TrendWaveState,
               pageOwner: RESTfulUserManager.User){
    GlobalScope.launch {
        val followManager = FollowManagerClass()
        if (!followManager.isFollowing(
                state.user?.uuid!!,
                pageOwner.uuid
            )
        ) {
            state.user?.let { it1 ->
                onEvent(
                    TrendWaveEvent.FollowEvent(
                        true,
                        it1.uuid,
                        pageOwner.uuid
                    )
                )
            }
            delay(50)
            onEvent(TrendWaveEvent.ClickCloseProfileScreen)
        } else {
            state.user?.let { it1 ->
                onEvent(
                    TrendWaveEvent.FollowEvent(
                        false,
                        it1.uuid,
                        pageOwner.uuid
                    )
                )
            }
            delay(50)
            onEvent(TrendWaveEvent.ClickCloseProfileScreen)

        }
    }
}

fun pinUser(onEvent: (TrendWaveEvent) -> Unit, state: TrendWaveState,
            pageOwner: RESTfulUserManager.User) {
    if (state.buttonshomescreen.toString().contains(pageOwner.username)) {
        GlobalScope.launch {
            PostButtonManager().buttonChange(false, 0, pageOwner.uuid, state.user!!.uuid, onEvent)
            delay(50)
            onEvent(TrendWaveEvent.ClickChangeHomeButtons)
        }
    } else {
        GlobalScope.launch {
            PostButtonManager().buttonChange(true, 0, pageOwner.uuid, state.user!!.uuid, onEvent)
            delay(50)
            onEvent(TrendWaveEvent.ClickChangeHomeButtons)
        }
    }
}