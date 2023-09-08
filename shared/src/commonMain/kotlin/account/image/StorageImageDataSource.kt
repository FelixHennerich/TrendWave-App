package account.image

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utilities.CommonLogger

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
        if(imageStorage.getImage(fileName) == null){
            getUrlByFileName(fileName)?.let {
                downloadImage(it,fileName)
            }
            while(imageStorage.getImage(fileName) == null){
                delay(1)
            }
        }
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

    /**
     * turns out a url of where an image can be found
     *
     * @param fileName -> Name of the new file
     * @return -> Url of Image
     */
    override fun getUrlByFileName(fileName: String): String? {
        return when(fileName){
            "Logo.jpg"                  -> "https://raw.githubusercontent.com/FelixHennerich/TrendWave-App/main/TrendWave%20Logo/Logo/256x256.png"
            else                        -> null
        }
    }
}