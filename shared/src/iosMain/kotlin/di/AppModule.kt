package di

import account.image.ImageDataSource
import account.image.ImageStorage
import account.image.StorageImageDataSource
import managers.DataStorageManager

actual class AppModule {

    /**
     * Imagedatasource to manage images
     */
    actual val imageDataSource: ImageDataSource by lazy {
        StorageImageDataSource(
            imageStorage = ImageStorage()
        )
    }

    actual val localDataSource: DataStorageManager by lazy {
        DataStorageManager()
    }
}