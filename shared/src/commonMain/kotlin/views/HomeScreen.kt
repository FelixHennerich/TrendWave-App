package views

import account.AppUser
import account.image.ImageDataSource
import account.presentation.ProfileSheet
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import compose.icons.FeatherIcons
import compose.icons.LineaIcons
import compose.icons.SimpleIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.GitPullRequest
import compose.icons.feathericons.Heart
import compose.icons.feathericons.User
import compose.icons.feathericons.UserCheck
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Bookmark
import compose.icons.tablericons.Message
import compose.icons.tablericons.Settings
import event.TrendWaveEvent
import event.TrendWaveState
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.RESTfulPostManager
import post.presentation.PostDisplay
import views.presentation.PostButton
import views.sheet.SettingsSheet
import post.presentation.addPostSheet
import utilities.color.Colors
import utilities.color.fromEnum

class HomeScreen {

    /**
     * HOME SCREEN Screen after login or register
     *
     * @param onEvent -> EventHandling in Viewmodel
     * @param state -> StateManager for PostAddSheet
     * @param localDataSource -> Get local data system
     * @param onNavigateLogin -> Navigate Login screen
     */
    @Composable
    fun HomeScreen(
        onEvent: (TrendWaveEvent) -> Unit,
        state: TrendWaveState,
        localDataSource: DataStorageManager,
        onNavigateLogin: () -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.background(Color.fromEnum(Colors.PRIMARY))
        ) {
            item {
                //First row icons
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Profile Page Icon
                    Icon(
                        imageVector = FeatherIcons.User,
                        contentDescription = "",
                        tint = Color.fromEnum(Colors.SENARY),
                        modifier = Modifier.offset(x = 20.dp).scale(.8f).clickable {
                            onEvent(TrendWaveEvent.ProfileHomeButton)
                        }
                    )

                    //Notification Page icon
                    Icon(
                        imageVector = TablerIcons.Bell,
                        contentDescription = "",
                        tint = Color.fromEnum(Colors.SENARY),
                        modifier = Modifier.padding(start = 250.dp).scale(.8f).clickable {

                        }
                    )

                    //Settings page icon
                    Icon(
                        imageVector = TablerIcons.Settings,
                        contentDescription = "",
                        tint = Color.fromEnum(Colors.SENARY),
                        modifier = Modifier.padding(end = 12.dp).scale(.8f).clickable {
                            onEvent(TrendWaveEvent.ClickSettingsScreen)
                        }
                    )
                }
            }

            item {
                //Top bar
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
                ) {
                    //Hello message
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Hello, ",
                            fontSize = 20.sp,
                            color = Color.fromEnum(Colors.SENARY),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 25.dp)
                        )
                        Text(
                            text = "${localDataSource.readString("username")}",
                            fontSize = 20.sp,
                            color = Color.fromEnum(Colors.SENARY),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                                .clickable { onEvent(TrendWaveEvent.ProfileHomeButton) }
                        )
                    }

                    //Follower and Following count
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        //Follower
                        Box() {
                            Text(
                                text = "Follower ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.fromEnum(Colors.SENARY),
                                modifier = Modifier.padding(start = 230.dp)
                            )
                            state.user?.let {
                                Text(
                                    text = it.follower,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.fromEnum(Colors.SENARY),
                                    modifier = Modifier.padding(top = 15.dp, start = 240.dp)
                                )
                            }
                        }

                        //Following
                        Box() {
                            Text(
                                text = "Following ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.fromEnum(Colors.SENARY),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            state.user?.let {
                                Text(
                                    text = it.following,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.fromEnum(Colors.SENARY),
                                    modifier = Modifier.padding(top = 15.dp, start = 20.dp)
                                )
                            }
                        }
                    }
                }

                //Button Bar
                Row(
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Post button
                    PostButton(
                        modifier = Modifier,
                        backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                        textcolor = Color.fromEnum(Colors.SENARY),
                        buttontext = "Post",
                        imageVector = TablerIcons.Message,
                        onEvent = onEvent,
                        event = TrendWaveEvent.ClickPostButton
                    )

                    //Saved posts
                    PostButton(
                        modifier = Modifier,
                        backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                        textcolor = Color.fromEnum(Colors.SENARY),
                        buttontext = "Saved",
                        imageVector = TablerIcons.Bookmark,
                        onEvent = onEvent,
                        event = TrendWaveEvent.TestHomeButton
                    )

                    //Following list
                    PostButton(
                        modifier = Modifier,
                        backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                        textcolor = Color.fromEnum(Colors.SENARY),
                        buttontext = "Following",
                        imageVector = FeatherIcons.UserCheck,
                        onEvent = onEvent,
                        event = TrendWaveEvent.FollowingHomeButton
                    )

                    //TODO
                    PostButton(
                        modifier = Modifier,
                        backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                        textcolor = Color.fromEnum(Colors.SENARY),
                        buttontext = "TODO",
                        imageVector = FeatherIcons.GitPullRequest,
                        onEvent = onEvent,
                        event = TrendWaveEvent.ClickSettingsScreen
                    )
                }

                //recent activity text
                Text(
                    text = "Recent activity",
                    modifier = Modifier.offset(x = 20.dp, y = 40.dp).padding(bottom = 50.dp),
                    fontSize = 20.sp,
                    color = Color.fromEnum(Colors.SENARY),
                    fontWeight = FontWeight.Bold
                )
            }

            item {

                var lastClickTime by remember { mutableStateOf(0L) }
                val delayMillis = 10000L

                if (state.posts.isEmpty()) {
                    val currentTime = getTimeMillis()
                    if (currentTime - lastClickTime >= delayMillis) {
                        GlobalScope.launch {
                            val restapi = RESTfulPostManager(state)
                            onEvent(TrendWaveEvent.UpdatePostList(restapi.getRandomPosts()))
                        }

                        lastClickTime = currentTime
                    }
                }
            }


            items(state.posts) { post ->
                PostDisplay(
                    modifier = Modifier,
                    backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                    textcolor = Color.fromEnum(Colors.SENARY),
                    iconbackgroundcolor = Color.fromEnum(Colors.QUINARY),
                    posttext = post.text,
                    postuser = post.username,
                    postuuid = post.uuid,
                    postdate = post.date,
                    postid = post.id,
                    localDataStorageManager = localDataSource,
                    onEvent = onEvent,
                    state = state,
                )
                Spacer(Modifier.height(6.dp))
            }
            item {
                Spacer(Modifier.height(100.dp))
            }
        }
        //Open Post screen
        addPostSheet(
            isOpen = state.isAddPostSheetOpen,
            onEvent = onEvent,
            localDataSource = localDataSource,
            state = state
        )

        //Open Settings
        SettingsSheet(
            isOpen = state.isSettingsSheetOpen,
            state = state,
            localDataSource = localDataSource,
            onEvent = onEvent,
            onLogout = onNavigateLogin
        )

        if (state.user != null) {
            //Open Profile
            ProfileSheet(
                isOpen = state.isProfileSheetOpen,
                onEvent = onEvent,
                state = state,
                localDataSource = localDataSource,
                pageOwner = state.user!!
            )
        }

        //open Profile
        if (state.watchUserProfile != null) {
            ProfileSheet(
                isOpen = state.isProfileUserSheetOpen,
                onEvent = onEvent,
                state = state,
                localDataSource = localDataSource,
                pageOwner = state.watchUserProfile!!
            )
        }
    }
}