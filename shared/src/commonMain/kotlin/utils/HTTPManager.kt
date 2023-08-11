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

    suspend fun post(url: String): HttpResponse {
        val jsonBody = """
        {
            "table": "newsapplication",
            "column": "b",
            "value": 20,
            "where": "test",
            "unit": 1
        }
        """.trimIndent()

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
        return response
    }
}