package account.image

expect class ImageStorage {

    suspend fun saveImage(bytes: ByteArray): String

    suspend fun getImage(fileName: String): ByteArray?

    suspend fun deleteImage(fileName: String)
}