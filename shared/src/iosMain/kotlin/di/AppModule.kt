package di

import account.image.ImageDataSource
import account.image.ImageStorage
import account.image.StorageImageDataSource

actual class AppModule {

    /**
     * Imagedatasource to manage images
     */
    actual val imageDataSource: ImageDataSource by lazy {
        StorageImageDataSource(
            imageStorage = ImageStorage()
        )
    }
}