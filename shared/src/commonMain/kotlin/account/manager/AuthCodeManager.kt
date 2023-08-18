package account.manager

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import managers.HTTPManager

class AuthCodeManager {

    private val client = HttpClient()
    suspend fun getNewAuthcode(): String {
        val response = client.get("https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/authentication/getNewAuthcode.php")
        val body = response.bodyAsText()
        var code: String = ""
        for(i in body.indices){
            if(body[i] == ':'){
                for(j in 1..32){
                    code += body[i+j]
                }
                break
            }
        }
        return code
    }

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