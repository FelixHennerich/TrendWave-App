package views

import account.AppUser
import account.image.ImageDataSource
import account.image.Photo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager

class LoadingScreen {

    /**
     * Screen during Loading
     *
     * @param imageDataSource -> Image handling
     */
    @Composable
    fun LoadingScreen(
        imageDataSource: ImageDataSource,
        localDataSource: DataStorageManager,
        state: TrendWaveState
    ) {
        if(localDataSource.readString("email") != null) {
            GlobalScope.launch {
                val userClass = AppUser(state)
                val uuid = localDataSource.readString("uuid")
                val role = uuid?.let { userClass.getRole(it) }

                if(localDataSource.readString("role") != role){
                    if (role != null) {
                        localDataSource.saveString("role", role)
                    }
                }
            }
        }



        var imageBytes by remember { mutableStateOf<ByteArray?>(null) }
        var loading by remember { mutableStateOf(true) }


        if (loading) {
            LaunchedEffect(loading) {
                imageBytes = imageDataSource.getImage("LogoTransparent.jpg")
                loading = false
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            imageBytes?.let {
                Photo(
                    width = 300.dp,
                    height = 300.dp,
                    photoBytes = imageBytes,
                )
            }
        }

    }
}