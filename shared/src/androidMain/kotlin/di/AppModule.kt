package di

import account.image.ImageDataSource
import account.image.ImageStorage
import account.image.StorageImageDataSource
import android.content.Context
import managers.DataStorageManager

actual class AppModule(
    private val context: Context
) {
    actual val imageDataSource: ImageDataSource by lazy {
        StorageImageDataSource(
            imageStorage = ImageStorage(context)
        )
    }
    actual val localDataSource: DataStorageManager by lazy {
        DataStorageManager(
            context
        )
    }

}