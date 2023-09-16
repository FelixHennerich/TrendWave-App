package account

import account.utilities.RoleType

interface UserInterface {

    suspend fun getEmail(uuid: String) : String
    suspend fun getUsername(uuid: String) : String
    suspend fun getUUID(email: String) : String
    suspend fun getRole(uuid: String) : String
    fun getBirthday(uuid: String) : String
    fun getSignupday(uuid: String) : String
    fun hasPermissionRole(role: RoleType) : Boolean

    suspend fun getFollower(uuid: String): String
    suspend fun getFollowing(uuid: String): String



}