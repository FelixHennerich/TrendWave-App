package account

import account.utilities.RoleType
import event.State
import event.TrendWaveState
import kotlinx.coroutines.flow.StateFlow

class AppUser: UserInterface {

    val restApi = RESTfulUserManager()
    val state = State.getState()

    /**
     * get user of database
     *
     * @param uuid -> Unique id
     * @param -> email
     */
    override suspend fun getUser(uuid: String): RESTfulUserManager.User {
        return restApi.findUserByUUID(uuid)
    }

    /**
     * get user of database
     *
     * @param username -> username
     * @param -> user
     */
    override suspend fun getUserByUsername(user: String): RESTfulUserManager.User {
        return restApi.findUserByUsername(user)
    }

    /**
     * get the email of a user
     * @param uuid -> Unique ID
     * @return -> email
     */
    override suspend fun getEmail(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.email
    }

    /**
     * get the password of a user
     * @param uuid -> Unique ID
     * @return -> password
     */
    override suspend fun getPassword(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.password
    }

    /**
     * get the username of a user
     * @param uuid -> Unique ID
     * @return -> username
     */
    override suspend fun getUsername(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.username
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
        val user = restApi.findUserByUUID(uuid)
        return user.role
    }

    /**
     * get the birthday of a user
     * @param uuid -> Unique ID
     * @return -> birthday
     */
    override suspend fun getBirthday(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.birthday
    }

    /**
     * get the signup of a user
     * @param uuid -> Unique ID
     * @return -> signup
     */
    override suspend fun getSignupday(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.signup
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
        val user = restApi.findUserByUUID(uuid)
        return user.follower
    }

    /**
     * get the following of a user
     * @param uuid -> Unique ID
     * @return -> following
     */
    override suspend fun getFollowing(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.following
    }

    /**
     * get user which are followed
     * @param uuid -> Unique ID
     * @return -> Userlist
     */
    override suspend fun getFollowed(uuid: String): String {
        val user = restApi.findUserByUUID(uuid)
        return user.followed
    }

    /**
     * get home buttons string
     * @param uuid -> Unique ID
     * @return -> String of buttons Watch postButtonManager
     */
    suspend fun getButtons(uuid: String): String{
        val user = restApi.findUserByUUID(uuid)
        return user.homebuttons
    }

}