package views

import account.AppUser
import account.presentation.ProfileSheet
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.Info
import compose.icons.feathericons.Plus
import compose.icons.feathericons.User
import compose.icons.tablericons.Message
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.presentation.MessageDisplay
import post.presentation.PostDisplay
import views.presentation.PostButton
import views.sheet.SettingsSheet
import post.presentation.addPostSheet
import utilities.CommonLogger
import utilities.color.Colors
import utilities.color.fromEnum
import utilities.presentation.BottomSheet
import views.presentation.PostButtonManager
import views.presentation.PostButtons

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
        onNavigateLogin: () -> Unit,
    ) {
        var isDropInfoVisable by remember { mutableStateOf(false) }
        var blurEffect by remember { mutableStateOf(0.dp) }
        val cornerrad = 10.dp
        var corner = RoundedCornerShape(cornerrad)
        val postbuttonlst = state.buttonshomescreen

        Scaffold(
            modifier = Modifier.clickable {
                //Click while Blur
                if (isDropInfoVisable) {
                    isDropInfoVisable = false
                    GlobalScope.launch {
                        for (i in 10 downTo 0) {
                            blurEffect = i.dp
                            delay(25)
                        }
                    }
                }
                //
            },
            bottomBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    //BottomSheet for Profile, Settigs and Notifications
                    BottomSheet(
                        visible = isDropInfoVisable,
                        modifier = Modifier.fillMaxWidth().weight(1f).height(200.dp),
                        backgroundcolor = Color.fromEnum(Colors.PRIMARY),
                        padding = 0.dp
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier.offset(y = 30.dp).padding(5.dp).clickable {
                                    onEvent(TrendWaveEvent.ClickProfileHomeButton)
                                },
                            ) {
                                Text(
                                    text = "Profile",
                                    fontSize = 20.sp,
                                    color = Color.fromEnum(Colors.SENARY)
                                )
                            }

                            Box(
                                modifier = Modifier.offset(y = 80.dp).padding(5.dp).clickable {
                                    onEvent(TrendWaveEvent.ClickSettingsScreen)
                                },
                            ) {
                                Text(
                                    text = "Settings",
                                    fontSize = 20.sp,
                                    color = Color.fromEnum(Colors.SENARY)
                                )
                            }
                            Box(
                                modifier = Modifier.offset(y = 130.dp).padding(5.dp).clickable {
                                    //TODO
                                },
                            ) {
                                Text(
                                    text = "Notifications",
                                    fontSize = 20.sp,
                                    color = Color.fromEnum(Colors.SENARY)
                                )
                            }
                        }
                    }

                }
            }) {
            //Normal Screen
            LazyColumn(
                modifier = Modifier.blur(blurEffect).background(Color.fromEnum(Colors.PRIMARY))
            ) {
                item {
                    //Top bar
                    Box(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp)
                            .height(120.dp)
                            .fillMaxWidth().background(
                                color = Color.fromEnum(Colors.QUATERNARY),
                                shape = corner
                            )
                    ) {
                        //Hello message
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Hello, ",
                                fontSize = 25.sp,
                                color = Color.fromEnum(Colors.SENARY),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 25.dp, top = 30.dp)
                            )
                            Text(
                                text = "${localDataSource.readString("username")}",
                                fontSize = 25.sp,
                                color = Color.fromEnum(Colors.SENARY),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f).padding(top = 30.dp)
                                    .clickable {
                                        //Click while Blur
                                        if (isDropInfoVisable) {
                                            isDropInfoVisable = false
                                            GlobalScope.launch {
                                                for (i in 10 downTo 0) {
                                                    blurEffect = i.dp
                                                    delay(25)
                                                }
                                            }
                                        }
                                        //
                                        onEvent(TrendWaveEvent.ClickProfileHomeButton)
                                    }
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Box(
                                modifier = Modifier.clickable {
                                    GlobalScope.launch {
                                        //Blur effect and animation
                                        if (!isDropInfoVisable) {
                                            isDropInfoVisable = true
                                            for (i in 0..10) {
                                                blurEffect = i.dp
                                                delay(25)
                                            }
                                        } else {
                                            isDropInfoVisable = false
                                            for (i in 10 downTo 0) {
                                                blurEffect = i.dp
                                                delay(25)
                                            }
                                        }
                                    }
                                },
                                contentAlignment = Alignment.TopEnd
                            ) {
                                Icon(
                                    imageVector = FeatherIcons.Info,
                                    contentDescription = "",
                                    tint = Color.fromEnum(Colors.SENARY),
                                    modifier = Modifier.scale(1f).padding(10.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    //Button Bar
                    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = 2)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                        state = lazyListState
                    ) {

                        //Add post
                        item {
                            //Post button
                            PostButton(
                                modifier = Modifier.padding(end = 10.dp),
                                backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                                textcolor = Color.fromEnum(Colors.SENARY),
                                imageVector = TablerIcons.Message,
                                text = null,
                                onEvent = onEvent,
                                event = TrendWaveEvent.ClickPostButton,
                                notclickable = isDropInfoVisable,
                                smallicon = FeatherIcons.Plus,
                                primarybackground = Color.fromEnum(Colors.PRIMARY),
                                addbutton = true
                            )
                        }

                        item {
                            //Post button
                            PostButton(
                                modifier = Modifier.padding(end = 10.dp),
                                backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                                textcolor = Color.fromEnum(Colors.SENARY),
                                imageVector = FeatherIcons.User,
                                text = null,
                                onEvent = onEvent,
                                event = TrendWaveEvent.TestHomeButton,
                                notclickable = isDropInfoVisable,
                                smallicon = FeatherIcons.Plus,
                                primarybackground = Color.fromEnum(Colors.PRIMARY),
                                addbutton = true
                            )
                        }


                        items(postbuttonlst) { entry ->
                            PostButton(
                                modifier = entry.modifier,
                                backgroundcolor = entry.backgroundcolor,
                                textcolor = entry.textcolor,
                                imageVector = entry.imageVector,
                                text = entry.text,
                                onEvent = entry.onEvent,
                                event = entry.event,
                                notclickable = entry.notclickable,
                                smallicon = entry.smallicon,
                                primarybackground = entry.primarybackground,
                                addbutton = entry.addbutton
                            )
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    //recent activity text
                    Text(
                        text = "Top Trending",
                        modifier = Modifier.offset(x = 20.dp).padding(bottom = 10.dp),
                        fontSize = 20.sp,
                        color = Color.fromEnum(Colors.SENARY),
                        fontWeight = FontWeight.Bold
                    )
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
                        posttheme = post.theme,
                        localDataStorageManager = localDataSource,
                        onEvent = onEvent,
                        state = state,
                        notclickable = isDropInfoVisable,
                    )
                    Spacer(Modifier.height(6.dp))
                }

                item {
                    Spacer(Modifier.height(100.dp))
                }

            }
        }
        //Open Post screen
        addPostSheet(
            isOpen = state.isAddPostSheetOpen,
            onEvent = onEvent,
            localDataSource = localDataSource,
            state = state,
            corner = corner,
            cornerrad = cornerrad
        )

        //Message Display screen
        MessageDisplay(
            visible = state.isMessageDisplaySheetOpen,
            onEvent = onEvent,
            backgroundcolor = Color.fromEnum(Colors.PRIMARY),
            corner = corner,
            modifier = Modifier.fillMaxSize(),
            authorname = state.messageDisplayAuthorname,
            posttext = state.messageDisplayMessageText,
            postdate = state.messageDisplayPostDate,
            postid = state.messageDisplayPostID,
            state = state,
        )

        //Open Settings
        SettingsSheet(
            isOpen = state.isSettingsSheetOpen,
            state = state,
            localDataSource = localDataSource,
            onEvent = onEvent,
            onLogout = onNavigateLogin,
            corner = corner
        )

        if (state.user != null) {
            //Open Profile
            ProfileSheet(
                isOpen = state.isProfileSheetOpen,
                onEvent = onEvent,
                state = state,
                localDataSource = localDataSource,
                pageOwneruuid = state.user!!.uuid
            )
        }

        //open Profile
        if (state.watchUserProfile != null) {
            ProfileSheet(
                isOpen = state.isProfileUserSheetOpen,
                onEvent = onEvent,
                state = state,
                localDataSource = localDataSource,
                pageOwneruuid = state.watchUserProfile!!.uuid
            )
        }
    }
}