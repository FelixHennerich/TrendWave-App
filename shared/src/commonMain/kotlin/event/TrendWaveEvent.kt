package event

import account.RESTfulUserManager
import post.Post

sealed interface TrendWaveEvent {
    class ChangeLoginErrorMessage(val message: String): TrendWaveEvent
    class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
    class ChangePostErrorMessage(val message: String): TrendWaveEvent
    class UpdatePostList(val posts: List<Post>): TrendWaveEvent
    class PostDeletionButton(val post: Post, val posts: List<Post>): TrendWaveEvent
    class UserPostLoading(val posts: List<Post>,val userposts: List<Post>, val uuid: String, val follower: String, val following: String): TrendWaveEvent
    class LocalPostCreation(val post: Post): TrendWaveEvent
    class LoadUserToLocal(val user: RESTfulUserManager.User): TrendWaveEvent
    class ClickUserProfileViewButton(val user: RESTfulUserManager.User): TrendWaveEvent
    class AddFollowedUser(val uuid: String, val following: String): TrendWaveEvent
    class RemoveFollowedUser(val uuid: String, val following: String): TrendWaveEvent
    class FollowUser(val uuid: String, val user: RESTfulUserManager.User): TrendWaveEvent
    class UnfollowUser(val uuid: String, val user: RESTfulUserManager.User): TrendWaveEvent
    object ClickPostButton: TrendWaveEvent
    object ClickSettingsScreen: TrendWaveEvent
    object TestHomeButton: TrendWaveEvent
    object ProfileHomeButton: TrendWaveEvent
    object ClickClosePostButton: TrendWaveEvent
    object ClickCloseSettingsScreen: TrendWaveEvent
    object ClickCloseProfileScreen: TrendWaveEvent
}