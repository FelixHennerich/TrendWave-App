package event.events

import account.AppUser
import account.manager.LoginManager
import event.Event
import event.TrendWaveEvent
import event.TrendWaveState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.RESTfulPostManager

class ApplicationStartEvent {

    private var _state: MutableStateFlow<TrendWaveState> = MutableStateFlow(TrendWaveState())

    /**
     * Method will be run when the app starts
     */
    fun onEvent(localDataSource: DataStorageManager,_state: MutableStateFlow<TrendWaveState>, restAPI: RESTfulPostManager){
        this._state = _state
        GlobalScope.launch {
            //is user logged in? -> Load data -> Load user posts
            if(loadUserData(localDataSource)){
                localDataSource.readString("uuid")?.let { loadUserPosts(it, restAPI) }
            }
            //Random posts laden
            loadRandomPosts(restAPI)
        }
    }

    /**
     * Load local data
     */
    suspend fun loadUserData(localDataSource: DataStorageManager): Boolean {
        val loginManager = LoginManager()
        val appUser = AppUser()
        if (loginManager.isLoggedIn(localDataSource)) {
            //Is logged in
            val uuid = localDataSource.readString("uuid")
            val username = localDataSource.readString("username")
            val role = localDataSource.readString("role")

            //Update role and username if changed
            uuid?.let {
                if (appUser.getRole(it) != role) {
                    localDataSource.deleteEntry("role")
                    localDataSource.saveString("role", appUser.getRole(it))
                }
                if (appUser.getRole(it) != username) {
                    localDataSource.deleteEntry("username")
                    localDataSource.saveString("username", appUser.getUsername(it))
                }
            }

            //Update the statemanager
            uuid?.let {
                _state.update {
                    it.copy(
                        isLoggedIn = true,
                        user = appUser.getUser(uuid)
                    )
                }
            }

            //Return logged in value
            return true
        } else {
            //Is not logged in
            _state.update {
                it.copy(
                    isLoggedIn = false
                )
            }

            return false // Not Logged in
        }
    }


    /**
     * Load user posts
     */
    suspend fun loadUserPosts(uuid: String, restAPI: RESTfulPostManager) {
        _state.update {it.copy(
            userposts = restAPI.getUserPosts(uuid),
            isDataLoaded = true
        )}
    }


    /**
     * Load Random posts
     */
    suspend fun loadRandomPosts(restAPI: RESTfulPostManager){
        if(_state.value.posts.isEmpty()){
            _state.update { it.copy(
                posts = restAPI.getRandomPosts()
            ) }
        }
    }


}