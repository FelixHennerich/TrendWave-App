package account.manager

import event.TrendWaveEvent
import event.TrendWaveState
import managers.DataStorageManager
import managers.exceptions.NException
import views.presentation.PostButtonManager

class LogoutManager(
    val state: TrendWaveState,
    val localDataManager: DataStorageManager,
    val onEvent: (TrendWaveEvent) -> Unit
) {

    fun logout(): NException{
        try {
            state.user = null
            state.posts = emptyList()
            state.userposts = null
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
            PostButtonManager().deleteLocalButtons(
                onEvent = onEvent
            )
            return NException.LogoutWorked121
        } catch (e: Exception){
            return NException.LogoutError120
        }
    }

}