package account.image

interface ImageDataSource {

    suspend fun getImage(fileName: String): ByteArray?
    suspend fun downloadImage(url: String): String?
    suspend fun downloadImage(url: String, fileName: String)
    suspend fun insertImage(bytes: ByteArray?): String?
    suspend fun deleteImage(filePath: String)

    fun getUrlByFileName(fileName: String): String?
}