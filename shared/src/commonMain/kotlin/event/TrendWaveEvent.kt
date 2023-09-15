package event

import post.Post

sealed interface TrendWaveEvent {
    class ChangeLoginErrorMessage(val message: String): TrendWaveEvent
    class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
    class UpdatePostList(val posts: List<Post>): TrendWaveEvent
    class PostDeletionButton(val post: Post, val posts: List<Post>): TrendWaveEvent
    class HomeScreen(val uuid: String): TrendWaveEvent
    class UserPostLoading(val posts: List<Post>): TrendWaveEvent
    object ClickPostButton: TrendWaveEvent
    object ClickSettingsScreen: TrendWaveEvent
    object TestHomeButton: TrendWaveEvent
    object ProfileHomeButton: TrendWaveEvent
    object ClickClosePostButton: TrendWaveEvent
    object ClickCloseSettingsScreen: TrendWaveEvent
}