package utilities

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ForgotPasswordAPI {

    private val client = HttpClient()
    private val url = "http://85.215.41.146/php/RESTfulAPI/"

    suspend fun getAuthcode() {
        val finurl = url + "emailAuthGetter.php"

        val response = client.get(finurl)
    }

    suspend fun getAuthCodeByUser(uuid: String): String?{
        val finurl = url + "emailAuthGetter.php"
        var code: String? = null

        val response = client.get(finurl)

        return code
    }
}