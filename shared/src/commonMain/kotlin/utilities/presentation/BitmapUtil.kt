package utilities.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Change Bytes to Bitmap
 *
 * @param bytes -> ByteArray
 * @return -> ImageBitmap to display images
 */
@Composable
expect fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap?