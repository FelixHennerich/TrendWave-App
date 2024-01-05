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

    /**
     * LocalDataSource -> LocalDataSystem to store and read information
     */
    actual val localDataSource: DataStorageManager by lazy {
        DataStorageManager()
    }

}