package account.manager

import event.TrendWaveState
import managers.DataStorageManager
import managers.exceptions.NException

class LogoutManager(
    val state: TrendWaveState,
    val localDataManager: DataStorageManager
) {

    fun logout(): NException{
        try {
            state.user = null
            state.posts = emptyList()
            state.userposts = null
            state.follower = null
            state.following = null
            state.watchUserProfile = null
            if (localDataManager.readString("email") != null &&
                localDataManager.readString("password") != null &&
                localDataManager.readString("username") != null &&
                localDataManager.readString("uuid") != null &&
                localDataManager.readString("role") != null
            ) {
                localDataManager.deleteEntry("email")
                localDataManager.deleteEntry("password")
                localDataManager.deleteEntry("username")
                localDataManager.deleteEntry("uuid")
                localDataManager.deleteEntry("role")
            }
            return NException.LogoutWorked121
        } catch (e: Exception){
            return NException.LogoutError120
        }
    }

}