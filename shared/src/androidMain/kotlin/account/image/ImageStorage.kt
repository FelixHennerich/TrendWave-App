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
    actual suspend fun saveImage(bytes: ByteArray): String{
        return withContext(Dispatchers.IO){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {outputStream ->
                outputStream.write(bytes)
            }
            fileName
        }
    }

    actual suspend fun getImage(fileName: String): ByteArray?{
        return withContext(Dispatchers.IO){
            context.openFileInput(fileName).use {inputStream ->
                inputStream.readBytes()
            }
        }
    }

    actual suspend fun deleteImage(fileName: String){
        return withContext(Dispatchers.IO){
            context.deleteFile(fileName)
        }
    }

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