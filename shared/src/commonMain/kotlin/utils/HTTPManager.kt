package utils

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*



class HTTPManager {
    private val client = HttpClient()

    suspend fun get(url: String): String {
        val response = client.get(url)
        return response.bodyAsText()
    }

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