package utils

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*



internal object HTTPManager {
    private val url = "https://cross-cultural-auto.000webhostapp.com/php/connectUpdate.php";
    fun post(){
        val client = HttpClient()

        //val response = client.get<String>(url)
    }
}