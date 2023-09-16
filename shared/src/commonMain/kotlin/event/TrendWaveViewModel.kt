package event


import account.User
import account.image.ImageDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrendWaveViewModel(
    private val imageDataSource: ImageDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(TrendWaveState())

    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TrendWaveState())

    val user = User()
    /**
     * Will watch every event
     *
     * @param event -> Eventlist of TrendWaveEvent
     */
    fun onEvent(event: TrendWaveEvent){
        when(event){
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
            is TrendWaveEvent.LocalPostCreation ->{
                _state.update { it.copy(
                    creationpost = event.post
                ) }
            }
            is TrendWaveEvent.ClickPostButton -> {
                _state.update { it.copy(
                    isAddPostSheetOpen = true
                ) }
            }
            is TrendWaveEvent.ClickClosePostButton -> {
                state.value.userposts?.let {
                    state.value.creationpost?.let {
                        _state.update {
                            it.copy(
                                isAddPostSheetOpen = false,
                                userposts = state.value.userposts?.plus(state.value.creationpost!!)
                            )
                        }
                    }
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
            is TrendWaveEvent.HomeScreen -> {
                _state.update { it.copy(
                    uuid = event.uuid
                ) }
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
            is TrendWaveEvent.ProfileHomeButton -> {
                _state.update { it.copy(
                    isProfileSheetOpen = true
                ) }
            }
            is TrendWaveEvent.UserPostLoading -> {
                GlobalScope.launch {
                    _state.update {
                        it.copy(
                            userposts = event.posts,
                            following = user.getFollowing(event.uuid),
                            follower = user.getFollower(event.uuid)
                        )
                    }
                }
            }
            is TrendWaveEvent.ClickCloseProfileScreen -> {
                _state.update {
                    it.copy(
                        isProfileSheetOpen = false
                    )
                }
            }
            else -> {}
        }
    }
}