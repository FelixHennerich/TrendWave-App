package event

import post.Post

data class TrendWaveState(
    var LoginErrorMessage: String? = null,
    var RegisterErrorMessage: String? = null,
    var isAddPostSheetOpen: Boolean = false,
    var isSettingsSheetOpen: Boolean = false,
    var posts: List<Post> = emptyList(),
    var uuid: String? = null
)
