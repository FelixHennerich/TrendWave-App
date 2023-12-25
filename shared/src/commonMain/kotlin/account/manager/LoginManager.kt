package account.manager

import account.AppUser
import managers.DataStorageManager
import managers.exceptions.ExceptionHandler
import managers.exceptions.NException
import utilities.textutils.EncryptionUtil

class LoginManager {

    /**
     * Method called when user tries to login within the app
     *
     * @param email -> of account
     * @param password -> not encrypted
     * @return -> Error Handeling
     */
    suspend fun login(email: String, password: String): NException{
        val user = AppUser()
        val encryptedPassword = EncryptionUtil.encryption(password)
        val passwordDB = user.getPassword(user.getUUID(email))

        if(encryptedPassword == passwordDB) {
            return NException.SUCCESS001
        }else {
            return NException.WrongPassword107
        }
    }



    /**
     * Check whether logged in or not
     *
     * @param localDataManager -> manage local data
     * @return Boolean -> logged in or not
     */
    suspend fun isLoggedIn(localDataManager: DataStorageManager): Boolean {
        if (localDataManager.readString("email") != null &&
            localDataManager.readString("password") != null &&
            localDataManager.readString("username") != null &&
            localDataManager.readString("uuid") != null &&
            localDataManager.readString("role") != null
        ) {
            val exceptionHandler = ExceptionHandler()
            val message = exceptionHandler.fetchErrorMessage(
                login(
                    email = localDataManager.readString("email")!!,
                    password = localDataManager.readString("password")!!
                )
            )

            if (message == exceptionHandler.fetchErrorMessage(NException.SUCCESS001)) {
                return true
            }
        }
        return false
    }


}