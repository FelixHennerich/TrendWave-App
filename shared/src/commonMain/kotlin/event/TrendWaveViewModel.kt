package event


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import event.events.ApplicationStartEvent
import event.events.FollowEvent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import managers.DataStorageManager
import post.RESTfulPostManager
import views.presentation.PostButtonManager

class TrendWaveViewModel(
    private val localDataStorageManager: DataStorageManager,
    private val restApi: RESTfulPostManager,
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
            //Post Screen
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

            //Settings screen
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

            //Profile Screen
            is TrendWaveEvent.ClickProfileHomeButton -> {
                _state.update { it.copy(
                    isProfileSheetOpen = true,
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
            is TrendWaveEvent.ClickChangeHomeButtons -> {
                _state.update { it.copy(
                    isProfileUserSheetOpen = false,
                    isProfileSheetOpen = false
                ) }
            }

            //Forgot password sheet
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

            //Post message display screen
            is TrendWaveEvent.ClickPostMessageDisplay -> {
                _state.update { it.copy(
                    messageDisplayAuthorname = event.authorname,
                    messageDisplayMessageText = event.posttext,
                    messageDisplayPostDate = event.postdate,
                    messageDisplayPostID = event.postid,
                    isMessageDisplaySheetOpen = true
                ) }
            }
            is TrendWaveEvent.ClickCloseMessageDisplay -> {
                _state.update {
                    it.copy(
                        isMessageDisplaySheetOpen = false,
                    )
                }
            }




            //Errormessages during login
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
            is TrendWaveEvent.LoadDataToCachePostButtons -> {
                val list = event.buttons
                _state.update { it.copy(
                    buttonshomescreen = list,
                    buttonshomescreenloaded = true,
                )}
            }
            is TrendWaveEvent.DeleteLocalHomeButtons -> {
                _state.update { it.copy(
                    buttonshomescreen = emptyList(),
                    buttonshomescreenloaded = false,
                ) }
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