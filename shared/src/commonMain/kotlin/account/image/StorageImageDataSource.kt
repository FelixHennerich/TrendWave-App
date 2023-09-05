package account.image

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StorageImageDataSource(
    private val imageStorage: ImageStorage
): ImageDataSource, ViewModel() {

    /**
     * Get image by fileName
     *
     * @param fileName
     * @return -> ByteArray of Image
     */
    override suspend fun getImage(fileName: String): ByteArray? {
        return imageStorage.getImage(fileName)
    }

    /**
     * Download image from the web
     *
     * @param url -> Url of the website
     * @return -> Filename of the saved image
     */
    override suspend fun downloadImage(url: String): String? {
        var fileName: String? = null
        GlobalScope.launch {
            val bytes = imageStorage.downloadImage(url)

            if (bytes != null) {
                fileName = imageStorage.saveImage(bytes)
            }
        }
        while(fileName == null){
            delay(1)
        }
        return fileName
    }

    /**
     * Download image from the web
     *
     * @param url -> Url of the website
     * @param fileName -> Filename of the saved image
     */
    override suspend fun downloadImage(url: String, fileName: String) {
        GlobalScope.launch {
            val bytes = imageStorage.downloadImage(url)

            if (bytes != null) {
                imageStorage.saveImage(bytes, fileName)
            }
        }
    }

    /**
     * save image in the local storage
     *
     * @param bytes -> Bytearray of image
     * @return -> fileName of image
     */
    override suspend fun insertImage(bytes: ByteArray?): String? {
        return bytes?.let { imageStorage.saveImage(it) }
    }

    /**
     * delete image from local storage
     *
     * @param filePath -> Path of file
     */
    override suspend fun deleteImage(filePath: String) {
        imageStorage.deleteImage(filePath)
    }
}