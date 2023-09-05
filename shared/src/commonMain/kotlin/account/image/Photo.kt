package account.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import utilities.presentation.rememberBitmapFromBytes

/**
 * Display a photo from bytes
 *
 * @param width -> width of image
 * @param height -> height of image
 * @param photoBytes -> ByteArray of iamge
 */
@Composable
fun Photo(
    width: Dp,
    height: Dp,
    photoBytes: ByteArray?
) {
    val bitmap = rememberBitmapFromBytes(photoBytes)
    val photoModifier = Modifier.width(width).height(height)

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = photoModifier,
            contentScale = ContentScale.Crop
        )
    }
}