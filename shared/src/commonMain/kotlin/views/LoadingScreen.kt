package views

import account.image.ImageDataSource
import account.image.Photo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import utilities.color.Colors
import utilities.color.fromEnum

class LoadingScreen {

    /**
     * Screen during Loading
     *
     * @param imageDataSource -> Image handling
     */
    @Composable
    fun LoadingScreen(
        imageDataSource: ImageDataSource
    ) {
        Column(
            modifier = Modifier.background(color = Color.fromEnum(Colors.PRIMARY))
        ) {
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
}