package event

import account.RESTfulUserManager
import post.Post
import views.presentation.PostButtons

data class TrendWaveState(
    var user: RESTfulUserManager.User? = null,
    var LoginErrorMessage: String? = null,
    var RegisterErrorMessage: String? = null,
    var createPostErrorMessage: String? = null,
    var isAddPostSheetOpen: Boolean = false,
    var isSettingsSheetOpen: Boolean = false,
    var isProfileSheetOpen: Boolean = false,
    var isProfileUserSheetOpen: Boolean = false,
    var isForgetPasswordSheetOpen: Boolean = false,
    var isMessageDisplaySheetOpen: Boolean = false,
    var messageDisplayAuthorname: String = "",
    var messageDisplayMessageText: String = "",
    var messageDisplayPostDate: String = "",
    var messageDisplayPostID: String = "",
    var posts: List<Post> = emptyList(),
    var userposts: List<Post>? = null, // TODO: ONLY FOR PROIFLE PAGE NOT FOR GENERAL USER PAGE
    var watchUserProfile: RESTfulUserManager.User? = null,
    var buttonshomescreen: List<PostButtons> = emptyList(),
    var buttonshomescreenloaded: Boolean = false,


    var isLoggedIn: Boolean = false,
    var isDataLoaded: Boolean = false,
)
