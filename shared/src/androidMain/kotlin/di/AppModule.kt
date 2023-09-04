package di

import account.image.ImageDataSource
import account.image.ImageStorage
import account.image.StorageImageDataSource
import android.content.Context

actual class AppModule(
    private val context: Context
) {
    actual val imageDataSource: ImageDataSource by lazy {
        StorageImageDataSource(
            imageStorage = ImageStorage(context)
        )
    }

}