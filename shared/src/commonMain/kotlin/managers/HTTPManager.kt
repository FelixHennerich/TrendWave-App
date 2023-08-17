package managers

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*



class HTTPManager {

    private val client = HttpClient()

    /**
     * Return whole HTTP body of given website
     *
     * @param url -> Website URL
     * @return -> HTTP Body
     */
    suspend fun get(url: String): String {
        val response = client.get(url)
        return response.bodyAsText()
    }

    /**
     * Return specific value from datebase
     *
     * @param url -> Website URL
     * @param value -> Value that is requested
     * @param uuid -> Unique ID
     * @param authcode -> Verification
     * @return -> requested Valuexx
     */
    suspend fun getValue(url: String, value: String, uuid: String, authcode: String): String{
        val response = client.get(url){
            url{
                parameters.append("value", value)
                parameters.append("uuid", uuid)
                parameters.append("authcode", authcode)
            }
        }
        return response.bodyAsText()
    }

    /**
     * Update account data in datebase using HTTP request
     *
     * @param url -> Website URL
     * @param table -> MySQL user table
     * @param column -> Column that will be updated
     * @param value -> Value that will be set
     * @param where -> UUID column where to edit
     * @param unit -> UUID of user
     * @return HTTPResponse OK/Failed etc.
     */
    suspend fun postUpdate(url: String, table: String, column: String, value: String, where: String, unit: String): HttpResponse {
        val jsonBody = """
        {
            "table": "$table",
            "column": "$column",
            "value": $value,
            "where": "$where",
            "unit": $unit
        }
        """.trimIndent()

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
        return response
    }

    /**
     * Create new account in datebase using HTTP request
     *
     * @param url -> Website URL
     * @param table -> MySQL user table
     * @param uuid -> Unique ID
     * @param email -> Email address
     * @param username -> Unique Username
     * @param password -> Encrypted password
     * @param signup -> Day of singup
     * @param birthday -> Birthday
     * @param role -> Role of user. DO NEVER CREATE A USER WITH ADMIN BY DEFAULT
     * @param authcode -> Authentication code for MySQL
     * @return HTTPResponse OK/Failed etc.
     */
    suspend fun postInsert(url: String, table: String, uuid: String,email:String,username:String, password:String, signup:String, birthday:String,role:String, authcode: String): HttpResponse {
        val jsonBody = """
        {
            "table": "$table",
            "uuid": "$uuid",
            "email": "$email",
            "username": "$username",
            "password": "$password",
            "signup": "$signup",
            "birthday": "$birthday",
            "role": "$role",
            "authcodetocheck": "$authcode"
        }
        """.trimIndent()

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
        return response
    }

    /**
     * MySQL post request check username validity
     *
     * @param url -> Website URL
     * @param table -> MySQL user table
     * @param username -> Username to check
     * @param authcode -> Authentication for MySQL
     * @return HTTP Body
     */
    suspend fun usernameCheck(url: String, table: String, username: String, authcode: String): String{

        val response = client.get(url) {
            url{
                parameters.append("table", table)
                parameters.append("username", username)
                parameters.append("authcodetocheck", authcode)
            }
        }
        return response.bodyAsText()
    }
}