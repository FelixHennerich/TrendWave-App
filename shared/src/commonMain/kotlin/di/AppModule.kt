package di

import account.image.ImageDataSource

expect class AppModule {
    val imageDataSource: ImageDataSource
}