package account

import account.utilities.RoleType

interface UserInterface {

    suspend fun getUser(uuid: String): RESTfulUserManager.User
    suspend fun getEmail(uuid: String) : String
    suspend fun getPassword(uuid: String) : String
    suspend fun getUsername(uuid: String) : String
    suspend fun getUUID(email: String) : String
    suspend fun getRole(uuid: String) : String
    suspend fun getBirthday(uuid: String) : String
    suspend fun getSignupday(uuid: String) : String
    fun hasPermissionRole(role: RoleType) : Boolean
    suspend fun getFollower(uuid: String): String
    suspend fun getFollowing(uuid: String): String



}