package event.events

import account.image.StorageImageDataSource
import account.manager.LoginManager
import event.Event
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import managers.DataStorageManager

class ApplicationStartEvent: Event {

    /**
     * Method will be run when the app starts
     */
    override fun onEvent(localDataSource: DataStorageManager,_state: MutableStateFlow<TrendWaveState>){
        GlobalScope.launch {
            loadUserData(localDataSource, _state)
        }
    }

    /**
     * Load local data
     */
    suspend fun loadUserData(localDataSource: DataStorageManager, _state: MutableStateFlow<TrendWaveState> = MutableStateFlow(TrendWaveState())) {
        val loginManager = LoginManager()
        if (loginManager.isLoggedIn(localDataSource)) {
            //Is logged in
            _state.update {
                it.copy(
                    isLoggedIn = true
                )
            }
            println("is logged in")
        } else {
            //Is not logged in
            _state.update {
                it.copy(
                    isLoggedIn = false
                )
            }
            println("is not logged in")
        }
    }


}