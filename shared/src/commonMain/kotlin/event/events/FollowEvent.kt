package event.events

import account.manager.FollowManagerClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FollowEvent {

    /**
     * Called on follow or unfollow
     * @param followtype -> follow = true, unfollow = false
     */
    fun onEvent(follow: Boolean, executeruuid: String, uuid: String){
        if(follow){
            followUser(executeruuid,uuid)
        }else {
            unfollowUser(executeruuid, uuid)
        }
    }

    fun followUser(executeruuid: String, uuid: String){
        val followManagerClass = FollowManagerClass()
        GlobalScope.launch {
            followManagerClass.followUser(executeruuid, uuid)
        }
    }

    fun unfollowUser(executeruuid: String, uuid: String){
        val followManagerClass = FollowManagerClass()
        GlobalScope.launch {
            followManagerClass.unfollowUser(executeruuid, uuid)
        }
    }



}