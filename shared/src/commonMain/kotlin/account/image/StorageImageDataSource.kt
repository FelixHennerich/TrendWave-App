package account.image

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StorageImageDataSource(
    private val imageStorage: ImageStorage
): ImageDataSource, ViewModel() {

    override suspend fun getImage(fileName: String): ByteArray? {
        return imageStorage.getImage(fileName)
    }

    override suspend fun downloadImage(url: String): String? {
        var fileName: String? = null
        GlobalScope.launch {
            val bytes = imageStorage.downloadImage(url)

            if (bytes != null) {
                fileName = imageStorage.saveImage(bytes)
            }
        }
        delay(5000L)
        return fileName
    }

    override suspend fun insertImage(bytes: ByteArray?): String? {
        return bytes?.let { imageStorage.saveImage(it) }
    }

    override suspend fun deleteImage(filePath: String) {
        imageStorage.deleteImage(filePath)
    }
}