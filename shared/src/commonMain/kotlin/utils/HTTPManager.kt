package utils

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*



class HTTPManager {
    private val url = "https://cross-cultural-auto.000webhostapp.com/php/connectUpdate.php";
    private val client = HttpClient()

    suspend fun get(): String {
        val response = client.get(url)
        return response.bodyAsText()
    }
}