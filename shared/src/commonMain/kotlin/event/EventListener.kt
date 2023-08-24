package event

interface EventListener {
    fun onEvent(event: Event)
}