package di

import account.image.ImageDataSource
import managers.DataStorageManager

expect class AppModule {
    val imageDataSource: ImageDataSource
    val localDataSource: DataStorageManager
}