package account

import account.utilities.RoleType

interface UserInterface {

    fun getEmail() : String
    fun getUsername() : String
    fun getUUID() : String
    fun getRole() : RoleType
    fun getBirthday() : String
    fun getSignupday() : String
    fun hasPermissionRole() : Boolean



}