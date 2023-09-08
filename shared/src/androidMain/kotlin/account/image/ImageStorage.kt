package account.image

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

actual class ImageStorage(
    private val context: Context
) {

    /**
     * save image in the local directory path of the device
     *
     * @param bytes -> Bytes that display the Image
     * @return -> fileName of the .jpg file
     */
    actual suspend fun saveImage(bytes: ByteArray): String{
        return withContext(Dispatchers.IO){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {outputStream ->
                outputStream.write(bytes)
            }
            fileName
        }
    }

    /**
     * save image in the local directory path of the device
     *
     * @param bytes -> Bytes that display the Image
     * @param fileName -> fileName of the .jpg file
     */
    actual suspend fun saveImage(bytes: ByteArray, fileName: String) {
        return withContext(Dispatchers.IO){
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {outputStream ->
                outputStream.write(bytes)
            }
        }
    }

    /**
     * Get Image by the name of the file
     *
     * @param fileName -> Name of file where the image is stored
     * @return ByteArray of the given image
     */
    actual suspend fun getImage(fileName: String): ByteArray?{
        println("test")
        try{
            return withContext(Dispatchers.IO) {
                context.openFileInput(fileName).use { inputStream ->
                    inputStream.readBytes()
                }
            }
        }catch (e: Exception){
            return null
        }
    }

    /**
     * Delete image from the Storage
     *
     * @param fileName -> Name of file that will be deleted
     */
    actual suspend fun deleteImage(fileName: String){
        return withContext(Dispatchers.IO){
            context.deleteFile(fileName)
        }
    }

    /**
     * Download an image from the internet
     *
     * @param url -> URL of the Image
     * @return ByteArray of the given image
     */
    actual suspend fun downloadImage(url: String): ByteArray?{
        try {
            val url = URL(url)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return null
            }

            val input: InputStream = connection.inputStream
            val output = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }

            return output.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}