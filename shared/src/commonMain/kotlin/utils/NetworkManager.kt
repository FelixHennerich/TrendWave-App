package utils

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class NetworkManager(private val httpClient: HttpClient) {
    suspend fun sendHttpRequest(url: String): HttpResponse {
        return httpClient.get(url)
    }
}