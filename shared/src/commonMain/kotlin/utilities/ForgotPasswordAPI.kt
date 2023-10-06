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

    fun getAuthCodeByUser(uuid: String): String?{
        var code: String? = null

        return code
    }
}