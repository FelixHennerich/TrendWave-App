package account.image

import android.content.Context
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import android.os.AsyncTask
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual class ImageDownloader{

    actual suspend fun downloadImage(url: String): ByteArray?{
        var bytes: ByteArray? = null

        GlobalScope.launch(Dispatchers.IO) {
            val imageData = downloadImageToByteArray(url)

            if (imageData != null) {
                bytes = imageData
            }
        }
        delay(5000L)
        return bytes
    }


    fun downloadImageToByteArray(imageUrl: String): ByteArray? {
        try {
            val url = URL(imageUrl)
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
