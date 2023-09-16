package event

import post.Post

data class TrendWaveState(
    var LoginErrorMessage: String? = null,
    var RegisterErrorMessage: String? = null,
    var isAddPostSheetOpen: Boolean = false,
    var isSettingsSheetOpen: Boolean = false,
    var isProfileSheetOpen: Boolean = false,
    var posts: List<Post> = emptyList(),
    var userposts: List<Post>? = null, // TODO: ONLY FOR PROIFLE PAGE NOT FOR GENERAL USER PAGE
    var follower: String? = null,
    var following: String? = null,
)
