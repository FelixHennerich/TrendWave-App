package event

class EventManager {

    private  val listeners = mutableListOf<EventListener>()

    /**
     * Add a new Listener to the Lists of watched events
     *
     * @param listener -> EventListener class
     */
    fun addListener(listener: EventListener){
        listeners.add(listener)
    }

    /**
     * Remove a Listener to the Lists of watched events
     *
     * @param listener -> EventListener class
     */
    fun removeListener(listener: EventListener){
        listeners.remove(listener)
    }

    /**
     * Call an event
     *
     * @param event -> Event based on listeners
     */
    fun dispatchEvent(event: Event){
        for(listener in listeners){
            listener.onEvent(event)
        }
    }
}