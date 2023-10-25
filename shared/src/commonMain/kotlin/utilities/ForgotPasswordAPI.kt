package utilities

import account.utilities.UUID
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ForgotPasswordAPI {

    private val client = HttpClient()
    private val url = "http://85.215.41.146/php/RESTfulAPI/"

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
}