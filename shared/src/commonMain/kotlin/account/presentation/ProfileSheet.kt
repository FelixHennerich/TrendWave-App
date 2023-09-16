package account.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import event.TrendWaveEvent
import event.TrendWaveState
import managers.DataStorageManager
import post.Post
import post.presentation.PostDisplay
import utilities.presentation.BottomSheet

@Composable
fun ProfileSheet(
    isOpen: Boolean,
    localDataSource: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState,
    posts: List<Post>,
    follower: String,
    following: String
) {
    BottomSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.White,
        padding = 0.dp
    ) {
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
                    text = "${localDataSource.readString("username")}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 260.dp)
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
                    text = "Follower: $follower",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
                Text(
                    text = "Following: $following",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.offset(y = 270.dp)
            ) {
                Text(
                    text = "${localDataSource.readString("username")}'s activity",
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