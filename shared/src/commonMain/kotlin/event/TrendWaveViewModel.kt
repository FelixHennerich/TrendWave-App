package event


import account.AppUser
import account.RESTfulUserManager
import account.image.ImageDataSource
import account.manager.LoginManager
import androidx.compose.runtime.collectAsState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import event.events.ApplicationStartEvent
import event.events.FollowEvent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import managers.DataStorageManager
import post.RESTfulPostManager
import utilities.CommonLogger

class TrendWaveViewModel(
    private val localDataStorageManager: DataStorageManager,
    private val restApi: RESTfulPostManager
) : ViewModel() {
    private val _state = MutableStateFlow(TrendWaveState())

    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TrendWaveState())

    /**
     * Will watch every event
     *
     * @param event -> Eventlist of TrendWaveEvent
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun onEvent(event: TrendWaveEvent){
        when(event){
            /**
             * Button Clicks
             */
            is TrendWaveEvent.ClickPostButton -> {
                _state.update { it.copy(
                    isAddPostSheetOpen = true
                ) }
            }
            is TrendWaveEvent.ClickClosePostButton -> {
                _state.update {
                    it.copy(
                        isAddPostSheetOpen = false,
                    )
                }
            }
            is TrendWaveEvent.ClickSettingsScreen -> {
                _state.update { it.copy(
                    isSettingsSheetOpen = true
                ) }
            }
            is TrendWaveEvent.ClickCloseSettingsScreen -> {
                _state.update { it.copy(
                    isSettingsSheetOpen = false
                ) }
            }
            is TrendWaveEvent.ClickProfileHomeButton -> {
                _state.update { it.copy(
                    isProfileSheetOpen = true
                ) }
            }
            is TrendWaveEvent.ClickCloseProfileScreen -> {
                _state.update {
                    it.copy(
                        isProfileSheetOpen = false,
                        isProfileUserSheetOpen = false
                    )
                }
            }
            is TrendWaveEvent.ClickUserProfileViewButton -> {
                _state.update { it.copy(
                    isProfileUserSheetOpen = true,
                    watchUserProfile = event.user
                ) }
            }
            is TrendWaveEvent.ClickForgotPasswordSheet -> {
                _state.update { it.copy(
                    isForgetPasswordSheetOpen = true
                ) }
            }
            is TrendWaveEvent.ClickCloseForgotPasswordSheet -> {
                _state.update { it.copy(
                    isForgetPasswordSheetOpen = false
                ) }
            }





            is TrendWaveEvent.ChangeRegisterErrorMessage -> {
                _state.update {it.copy(
                    RegisterErrorMessage = event.message
                ) }
            }
            is TrendWaveEvent.ChangeLoginErrorMessage -> {
                _state.update {it.copy(
                    LoginErrorMessage = event.message
                ) }
            }
            is TrendWaveEvent.ChangePostErrorMessage -> {
                _state.update { it.copy(
                    createPostErrorMessage = event.message
                ) }
            }
            is TrendWaveEvent.LocalPostCreation -> {
                _state.update {
                    it.copy(
                        userposts = state.value.userposts?.plus(event.post),
                        posts = state.value.posts.plus(event.post)
                    )
                }
            }
            is TrendWaveEvent.UpdatePostList -> {
                _state.update {it.copy(
                    posts = event.posts
                ) }
            }
            is TrendWaveEvent.PostDeletionButton -> {
                _state.update { it.copy(
                    posts = event.posts - event.post
                ) }
                state.value.userposts?.let {
                    if (state.value.userposts!!.contains(event.post)) {
                        _state.update {
                            it.copy(
                                userposts = state.value.userposts?.minus((event.post))
                            )
                        }
                    }
                }
            }
            is TrendWaveEvent.ApplicationStartEvent -> {
                val applicationStartEvent = ApplicationStartEvent()
                applicationStartEvent.onEvent(
                    localDataSource = localDataStorageManager,
                    _state = _state,
                    restAPI = restApi
                )
            }
            is TrendWaveEvent.FollowEvent -> {
                val followEvent = FollowEvent()
                followEvent.onEvent(
                    follow = event.follow,
                    executeruuid = event.executeruuid,
                    uuid = event.uuid,
                )
            }
            else -> {}
        }
    }
}