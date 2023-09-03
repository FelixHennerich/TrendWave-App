package account.image

expect class ImageDownloader {


    constructor()

    suspend fun downloadImage(url: String): ByteArray?

}