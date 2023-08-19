package account.manager

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import managers.HTTPManager

class AuthCodeManager {

    private val client = HttpClient()

    /**
     * Authcode is needed to authenticate every mysql request using HTTP
     *
     * @return -> Unique Authcode 128Bit
     */
    suspend fun getNewAuthcode(): String {
        val response = client.get("https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/authentication/getNewAuthcode.php")
        val body = response.bodyAsText()
        var code = buildString { // Stringbuilder 120x more performance saving than Strings
            for (i in body.indices) {
                if (body[i] == ':') {
                    for (j in 1..32) {
                        append(body[i + j])
                    }
                    break
                }
            }
        }
        return code
    }


    /**
     * Deactivate the authcode for mysql request after every use of it
     *
     * @param authcode -> Code that will be deactivated
     * @return -> Return body html
     */
    suspend fun deactivateAuthcode(authcode: String): String{
        delay(100)
        val response = client.get("https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/authentication/deactivateAuthcode.php") {
            url{
                parameters.append("authcode", authcode)
            }
        }
        val body = response.bodyAsText()
        return body
    }
}