package event


import account.AppUser
import account.RESTfulUserManager
import account.image.ImageDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utilities.CommonLogger

class TrendWaveViewModel(
    private val imageDataSource: ImageDataSource,
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
            is TrendWaveEvent.FollowUser -> {
                val newUser = event.user
                newUser.followed = "${newUser.followed}#${event.uuid}"
                _state.update { it.copy(
                    user = newUser
                ) }
            }
            is TrendWaveEvent.UnfollowUser -> {
                val newUser = event.user
                val lst = newUser.followed.split("#")

                val newfollowed = buildString {
                    for(entry in lst)   {
                        append("$entry#")
                    }
                }

                newUser.followed = newfollowed
                _state.update { it.copy(
                    user = newUser
                ) }
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
                            posts = event.posts,
                            userposts = event.userposts,
                            following = event.following,
                            follower = event.follower
                        )
                    }
                }
            }
            is TrendWaveEvent.ClickCloseProfileScreen -> {
                _state.update {
                    it.copy(
                        isProfileSheetOpen = false,
                        isProfileUserSheetOpen = false
                    )
                }
            }
            is TrendWaveEvent.LoadUserToLocal -> {
                _state.update { it.copy(
                    user = event.user
                ) }
            }
            is TrendWaveEvent.ClickUserProfileViewButton -> {
                _state.update { it.copy(
                    isProfileUserSheetOpen = true,
                    watchUserProfile = event.user
                ) }
            }
            is TrendWaveEvent.RemoveFollowedUser -> {
                var lst = state.value.user?.followed?.split("#")
                lst = lst?.minus(event.uuid)

                val string = buildString {
                    if (lst != null) {
                        for(entry in lst){
                            append("#$entry")
                        }
                    }
                }
                val user = state.value.user
                if (user != null) {
                    user.followed = string
                    user.following = event.following
                }
                _state.update { it.copy(
                    user = user
                ) }
            }
            is TrendWaveEvent.AddFollowedUser -> {
                val lst = state.value.user?.followed?.split("#")
                lst?.plus(event.uuid)

                val string = buildString {
                    if (lst != null) {
                        for(entry in lst){
                            append("#$entry")
                        }
                    }
                }
                val user = state.value.user
                if (user != null) {
                    user.followed = string
                    user.following = event.following
                }
                _state.update { it.copy(
                    user = user
                ) }
            }
            is TrendWaveEvent.ClickForgotPasswordSheet -> {
                _state.update { it.copy(
                    isForgetPasswordSheetOpen = true
                ) }
            }is TrendWaveEvent.ClickCloseForgotPasswordSheet -> {
                _state.update { it.copy(
                    isForgetPasswordSheetOpen = false
                ) }
            }
            else -> {}
        }
    }
}