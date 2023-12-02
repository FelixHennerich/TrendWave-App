package event

import account.RESTfulUserManager
import post.Post

sealed interface TrendWaveEvent {

    /**
     * Button Clicks
     */
    object ClickClosePostButton: TrendWaveEvent
    object ClickCloseSettingsScreen: TrendWaveEvent
    object ClickCloseProfileScreen: TrendWaveEvent
    object ClickForgotPasswordSheet: TrendWaveEvent
    object ClickCloseForgotPasswordSheet: TrendWaveEvent
    object ClickPostButton: TrendWaveEvent
    object ClickSettingsScreen: TrendWaveEvent
    object ClickProfileHomeButton: TrendWaveEvent
    class ClickUserProfileViewButton(val user: RESTfulUserManager.User): TrendWaveEvent




    /**
     * Events
     */
    object ApplicationStartEvent: TrendWaveEvent




    class ChangeLoginErrorMessage(val message: String): TrendWaveEvent
    class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
    class ChangePostErrorMessage(val message: String): TrendWaveEvent
    class UpdatePostList(val posts: List<Post>): TrendWaveEvent
    class PostDeletionButton(val post: Post, val posts: List<Post>): TrendWaveEvent
    class LocalPostCreation(val post: Post): TrendWaveEvent
    class AddFollowedUser(val uuid: String, val following: String): TrendWaveEvent
    class RemoveFollowedUser(val uuid: String, val following: String): TrendWaveEvent
    class FollowUser(val uuid: String, val user: RESTfulUserManager.User): TrendWaveEvent
    class UnfollowUser(val uuid: String, val user: RESTfulUserManager.User): TrendWaveEvent
    object TestHomeButton: TrendWaveEvent
    object FollowingHomeButton: TrendWaveEvent
}