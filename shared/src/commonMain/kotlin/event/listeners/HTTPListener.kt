package event.listeners

import account.manager.AuthCodeManager
import event.Event
import event.EventListener
import event.EventType
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HTTPListener : EventListener {


    /**
     * Called on HTTP Requests
     *
     * @param event -> Based on listeners
     */
    override fun onEvent(event: Event) {
        when (event.type) {
            EventType.HTTPListener -> {
                println("Working")
            }
        }
    }
}