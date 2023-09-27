package account

import account.utilities.RoleType
import event.TrendWaveState
import kotlinx.coroutines.flow.StateFlow

class AppUser(
    private val state: TrendWaveState
): UserInterface {

    private val url: String
        get() = "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/connectGet.php"
    val restApi = RESTfulUserManager()

    /**
     * get user of database
     *
     * @param uuid -> Unique id
     * @param -> email
     */
    override suspend fun getUser(uuid: String): RESTfulUserManager.User {
        if(state.user?.uuid != uuid) {
            return restApi.findUserByUUID(uuid)
        }else {
            return state.user!!
        }
    }

    /**
     * get user of database
     *
     * @param username -> username
     * @param -> user
     */
    override suspend fun getUserByUsername(user: String): RESTfulUserManager.User {
        if(state.user?.username != user) {
            return restApi.findUserByUsername(user)
        }else {
            return state.user!!
        }
    }

    /**
     * get the email of a user
     * @param uuid -> Unique ID
     * @return -> email
     */
    override suspend fun getEmail(uuid: String): String {
        if(state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.email
        }else {
            return state.user!!.email
        }
    }

    /**
     * get the password of a user
     * @param uuid -> Unique ID
     * @return -> password
     */
    override suspend fun getPassword(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.password
        } else {
            return state.user!!.password
        }
    }

    /**
     * get the username of a user
     * @param uuid -> Unique ID
     * @return -> username
     */
    override suspend fun getUsername(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.username
        } else {
            return state.user!!.username
        }
    }

    /**
     * get the uuid of a user
     * @param email -> email
     * @return -> uuid
     */
    override suspend fun getUUID(email: String): String {
        val user = restApi.findUserByEmail(email)
        return user.uuid
    }

    /**
     * get the role of a user
     * @param uuid -> Unique ID
     * @return -> role
     */
    override suspend fun getRole(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.role
        } else {
            return state.user!!.role
        }
    }

    /**
     * get the birthday of a user
     * @param uuid -> Unique ID
     * @return -> birthday
     */
    override suspend fun getBirthday(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.birthday
        } else {
            return state.user!!.birthday
        }
    }

    /**
     * get the signup of a user
     * @param uuid -> Unique ID
     * @return -> signup
     */
    override suspend fun getSignupday(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.signup
        } else {
            return state.user!!.signup
        }
    }

    override fun hasPermissionRole(role: RoleType): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * get the follower of a user
     * @param uuid -> Unique ID
     * @return -> follower
     */
    override suspend fun getFollower(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.follower
        } else {
            return state.user!!.follower
        }
    }

    /**
     * get the following of a user
     * @param uuid -> Unique ID
     * @return -> following
     */
    override suspend fun getFollowing(uuid: String): String {
        if (state.user?.uuid != uuid) {
            val user = restApi.findUserByUUID(uuid)
            return user.following
        } else {
            return state.user!!.following
        }
    }

    /**
     * get user which are followed
     * @param uuid -> Unique ID
     * @return -> Userlist
     */
    override suspend fun getFollowed(uuid: String): String{
        if(state.user?.uuid != uuid){
            val user = restApi.findUserByUUID(uuid)
            return user.followed
        }else {
            return state.user!!.followed
        }
    }

}