package account.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import utilities.presentation.rememberBitmapFromBytes


@Composable
fun Photo(
    modifier: Modifier = Modifier,
    photoBytes: ByteArray?
) {
    val bitmap = rememberBitmapFromBytes(photoBytes)
    val photoModifier = modifier.clip(RoundedCornerShape(30))

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = photoModifier,
            contentScale = ContentScale.Crop
        )
    }
}