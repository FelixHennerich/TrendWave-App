package account

import account.utilities.RoleType

interface UserInterface {

    fun getEmail(uuid: String, authcode: String) : String
    fun getUsername(uuid: String) : String
    fun getUUID(email: String) : String
    fun getRole(uuid: String) : RoleType
    fun getBirthday(uuid: String) : String
    fun getSignupday(uuid: String) : String
    fun hasPermissionRole(role: RoleType) : Boolean



}