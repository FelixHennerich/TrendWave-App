package account.image

import io.kamel.core.utils.URL
import io.ktor.http.Url
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import nl.adaptivity.xmlutil.core.impl.multiplatform.InputStream
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.getBytes
import platform.Foundation.stringByAppendingPathComponent
import platform.Foundation.writeToFile
import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.utils.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.InternalAPI
import io.ktor.utils.io.*
import io.ktor.utils.io.core.readBytes
import utilities.CommonLogger

actual class ImageStorage {

    private val fileManager = NSFileManager.defaultManager
    private val documentDirectory = NSSearchPathForDirectoriesInDomains(
        directory = NSDocumentDirectory,
        domainMask = NSUserDomainMask,
        expandTilde = true
    ).first() as NSString

    /**
     * save image in the local directory path of the device
     *
     * @param bytes -> Bytes that display the Image
     * @return -> fileName of the .jpg file
     */
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun saveImage(bytes: ByteArray): String{
        return withContext(Dispatchers.Default) {
            val fileName = NSUUID.UUID().UUIDString + ".jpg"
            val fullPath = documentDirectory.stringByAppendingPathComponent(fileName)

            val data = bytes.usePinned {
                NSData.create(
                    bytes = it.addressOf(0),
                    length = bytes.size.toULong()
                )
            }

            data.writeToFile(
                path = fullPath,
                atomically = true
            )

            fullPath
        }
    }

    /**
     * save image in the local directory path of the device
     *
     * @param bytes -> Bytes that display the Image
     * @param fileName -> fileName of the .jpg file
     */
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun saveImage(bytes: ByteArray, fileName: String){
        withContext(Dispatchers.Default) {
            val fullPath = documentDirectory.stringByAppendingPathComponent(fileName)

            val data = bytes.usePinned {
                NSData.create(
                    bytes = it.addressOf(0),
                    length = bytes.size.toULong()
                )
            }

            data.writeToFile(
                path = fullPath,
                atomically = true
            )
        }
    }

    /**
     * Get Image by the name of the file
     *
     * @param fileName -> Name of file where the image is stored
     * @return ByteArray of the given image
     */
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getImage(fileName: String): ByteArray? {
        return withContext(Dispatchers.Default) {
            memScoped {
                NSData.dataWithContentsOfFile(documentDirectory.stringByAppendingPathComponent(fileName))?.let { bytes ->
                    val array = ByteArray(bytes.length.toInt())
                    bytes.getBytes(array.refTo(0).getPointer(this), bytes.length)
                    return@withContext array
                }
            }
            return@withContext null
        }
    }

    /**
     * Delete image from the Storage
     *
     * @param fileName -> Name of file that will be deleted
     */
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun deleteImage(fileName:String){
        withContext(Dispatchers.Default){
            fileManager.removeItemAtPath(fileName, null)
        }
    }

    /**
     * Download an image from the internet
     *
     * @param url -> URL of the Image
     * @return ByteArray of the given image
     */
    @OptIn(InternalAPI::class)
    actual suspend fun downloadImage(url: String): ByteArray? {
        val client = HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
        }

        return try {
            val response: HttpResponse = client.get(url)
            val content: ByteReadChannel = response.content

            val byteArray = content.readRemaining().readBytes()

            byteArray
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            client.close()
        }
    }

}