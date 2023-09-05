package account.image

expect class ImageStorage {

    suspend fun saveImage(bytes: ByteArray): String

    suspend fun saveImage(bytes: ByteArray, fileName: String)

    suspend fun getImage(fileName: String): ByteArray?

    suspend fun deleteImage(fileName: String)

    suspend fun downloadImage(url: String): ByteArray?
}