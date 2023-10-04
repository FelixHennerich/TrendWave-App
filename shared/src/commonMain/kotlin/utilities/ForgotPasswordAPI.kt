package utilities

import io.ktor.client.HttpClient

class ForgotPasswordAPI {

    private val client = HttpClient()
    private val url = "http://85.215.41.146/php/RESTfulAPI/"

    fun getAuthcode() {
        val finurl = url + "emailAuthGetter.php"

    }

    fun getAuthCodeByUser(uuid: String): String?{
        var code: String? = null

        return code
    }
}