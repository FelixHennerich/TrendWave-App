package account.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.Post
import post.PostLoader
import post.presentation.PostDisplay
import utilities.presentation.BottomSheet

@Composable
fun ProfileSheet(
    isOpen: Boolean,
    localDataSource: DataStorageManager,
    onEvent: (TrendWaveEvent) -> Unit,
    state: TrendWaveState,
    posts: List<Post>
) {
    BottomSheet(
        visible = isOpen,
        modifier = Modifier.fillMaxSize(),
        backgroundcolor = Color.White
    ) {
        LazyColumn {
            item{
                Text("Test")
            }
            items(posts) { post ->
                PostDisplay(
                    modifier = Modifier,
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