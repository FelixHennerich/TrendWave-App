package account.manager

import account.AppUser
import event.TrendWaveEvent
import event.TrendWaveState
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import managers.exceptions.NException
import utilities.CommonLogger

class FollowManagerClass{
    private val client = HttpClient()
    private val url = "https://felix.henneri.ch/php/RESTfulAPI/"

    /**
     * Unfollow a user
     *
     * @param unfollower -> Person who clicks "unfollow"
     * @param unfollowed -> Person that will be unfollowed
     * @return NException -> Exception Handling
     */
    suspend fun unfollowUser(unfollower: String, unfollowed: String): NException{
        if(!isFollowing(unfollower, unfollowed)){
            return NException.IsNotFollowing201
        }

        removeFollow(unfollowed)
        removeFollowing(unfollower, unfollowed)
        return NException.SUCCESS001
    }

    /**
     * follow a user
     *
     * @param follower -> Person who clicks "follow"
     * @param followed -> Person that will be followed
     * @return NException -> Exception Handling
     */
    suspend fun followUser(follower: String, followed: String): NException{
        if(isFollowing(follower, followed)){
            return NException.IsAlreadyFollowing200
        }

        addFollow(followed)
        addFollowing(follower, followed)
        return NException.SUCCESS001
    }

    /**
     * Add a follow in the database
     *
     * @param uuid -> Unique ID of person that gets a follow
     */
    suspend fun addFollow(uuid: String){
        val user = AppUser()
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

    /**
     * remove a follow in the database
     *
     * @param uuid -> Unique Id if person that gets a follow removed
     */
    suspend fun removeFollow(uuid: String){
        val user = AppUser()
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

    /**
     * remove 1 point of the following value
     *
     * @param uuid -> Person that clicks "unfollow"
     * @param followed -> Person that will be unfollowed
     */
    suspend fun removeFollowing(uuid: String, followed: String){
        val user = AppUser()
        var currentfollows = user.getFollowed(uuid).split("#")

        val followers = buildString {
            for(entry in currentfollows){
                if(entry != "" && entry != followed) {
                    append("$entry#")
                }
            }
            append("#")
        }

        val currentfollowing = user.getFollowing(uuid)
        val following = (currentfollowing.toInt() - 1)
        val finurl = url + "followGetter.php"


        client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("following", following.toString())
                parameters.append("followed", followers)
                parameters.append("remove", "true")
            }
        }
    }

    /**
     * add following point
     *
     * @param uuid -> Person that clicks "follow"
     * @param followed -> Person that gets the follow
     */
    suspend fun addFollowing(uuid: String, followed: String){
        val user = AppUser()
        val currentfollows = user.getFollowed(uuid)
        val followers = "$currentfollows#$followed"

        val currentfollowing = user.getFollowing(uuid)
        val following = (currentfollowing.toInt() + 1)

        val finurl = url + "followGetter.php"


        client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("following", following.toString())
                parameters.append("followed", followers)
                parameters.append("add", "true")
            }
        }
    }

    /**
     * Request whether a person is already following another
     *
     * @param uuid -> Person who will be checked if he followes someone
     * @param followed -> Person who will be checked if uuid follows hims
     * @return true or false
     */
    suspend fun isFollowing(uuid: String, followed: String): Boolean{
        val user = AppUser()
        val currentfollows = user.getFollowed(uuid)
        return currentfollows.contains(followed)
    }

}