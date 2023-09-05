package account.image

import kotlinx.coroutines.GlobalScope

class ImageDownloader{

    /**
     * Method will be performed on the first start of the app
     * to download all images in the local storage
     *
     * @param imageDataSource -> Image methods
     */
    suspend fun firstImageDownloader(imageDataSource: ImageDataSource){
        val applogo = "Applogo.jgp"
        val applogolink = "https://raw.githubusercontent.com/FelixHennerich/TrendWave-App/main/TrendWave%20Logo/Logo/256x256.png"

        if(imageDataSource.getImage(applogo) == null){
            imageDataSource.downloadImage(applogolink, applogo)
        }
    }

}