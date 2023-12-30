package utilities

import account.utilities.UUID
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay

class ForgotPasswordAPI {

    private val client = HttpClient()
    private val url = "https://felix.henneri.ch/php/RESTfulAPI/"

    /**
     * Provide a new authcode for passwordresets for specific user
     *
     * @param uuid -> Unique User ID
     * @return authcode 128Bit added in database
     */
    suspend fun createAuthcode(uuid: String): String {
        val finurl = url + "emailAuthGetter.php"
        val uuidclass = UUID()
        val code = uuidclass.generate128BitUUID()

        val response = client.get(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("code", code)
            }
        }

        while (!response.bodyAsText().contains("okay")){
            delay(10)
        }

        return code
    }

    /**
     * provides an authcode for passwordreset by a specific user
     *
     * @param uuid -> Unique user iD
     * @return authcode
     */
    suspend fun getAuthCodeByUser(uuid: String): String?{
        val finurl = url + "emailAuthGetter.php"
        var code: String? = null

        val response = client.get(finurl) {
            url {
                parameters.append("uuid", uuid)
            }
        }

        return code
    }

    suspend fun sendMail(code: String, email: String): String{
        val finurl = url + "sendEmail.php"
        var lst = email.split("@")
        val first = lst[0]
        val second = lst[1]

        val commonLogger = CommonLogger()
        commonLogger.log(first)
        commonLogger.log(second)
        val response = client.get(finurl) {
            url {
                parameters.append("code", code)
                parameters.append("first", first)
                parameters.append("second", second)
            }
        }

        return response.bodyAsText()
    }
}