package account.manager

import account.AppUser
import event.TrendWaveEvent
import event.TrendWaveState
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import managers.exceptions.NException
import utilities.CommonLogger

class FollowManager(
    private val state: TrendWaveState,
    private val onEvent: (TrendWaveEvent) -> Unit
) {
    private val client = HttpClient()
    private val url = "http://85.215.41.146/php/RESTfulAPI/"

    suspend fun unfollowUser(unfollower: String, unfollowed: String): NException{
        if(!isFollowing(unfollower, unfollowed)){
            return NException.IsNotFollowing201
        }

        removeFollow(unfollowed)
        removeFollowing(unfollower, unfollowed)
        return NException.SUCCESS001
    }

    suspend fun followUser(follower: String, followed: String): NException{
        if(isFollowing(follower, followed)){
            return NException.IsAlreadyFollowing200
        }

        addFollow(followed)
        addFollowing(follower, followed)
        return NException.SUCCESS001
    }

    suspend fun addFollow(uuid: String){
        val user = AppUser(state)
        val currentfollows = user.getFollower(uuid).toInt()
        val followers = (currentfollows + 1)
        val finurl = url + "followGetter.php"

        client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("follower", followers.toString())
                parameters.append("add", "true")
            }
        }
    }

    suspend fun removeFollow(uuid: String){
        val user = AppUser(state)
        val currentfollows = user.getFollower(uuid).toInt()
        val followers = (currentfollows - 1)
        val finurl = url + "followGetter.php"

        client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("follower", followers.toString())
                parameters.append("remove", "true")
            }
        }
    }

    suspend fun removeFollowing(uuid: String, followed: String){
        val user = AppUser(state)
        val currentfollows = user.getFollowed(uuid).split("#")
        currentfollows.minus(followed)

        val followers = buildString {
            for(entry in currentfollows){
                append("#$entry")
            }
        }

        val currentfollowing = user.getFollowing(uuid)
        val following = (currentfollowing.toInt() - 1)

        val finurl = url + "followGetter.php"

        onEvent(TrendWaveEvent.RemoveFollowedUser("#$followed"))

        client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("following", following.toString())
                parameters.append("followed", followers)
                parameters.append("remove", "true")
            }
        }
    }

    suspend fun addFollowing(uuid: String, followed: String){
        val user = AppUser(state)
        val currentfollows = user.getFollowed(uuid)
        val followers = "$currentfollows#$followed"

        val currentfollowing = user.getFollowing(uuid)
        val following = (currentfollowing.toInt() + 1)

        val finurl = url + "followGetter.php"

        onEvent(TrendWaveEvent.AddFollowedUser("#$followed"))

        client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("following", following.toString())
                parameters.append("followed", followers)
                parameters.append("add", "true")
            }
        }
    }

    suspend fun isFollowing(uuid: String, followed: String): Boolean{
        val user = AppUser(state)
        val currentfollows = user.getFollowed(uuid)
        return currentfollows.contains(followed)
    }

}