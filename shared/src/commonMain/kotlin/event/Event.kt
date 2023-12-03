package event

import account.manager.LoginManager
import kotlinx.coroutines.flow.MutableStateFlow
import managers.DataStorageManager
import post.RESTfulPostManager

interface Event {

    fun onEvent(localDataSource: DataStorageManager, _state: MutableStateFlow<TrendWaveState>)

}