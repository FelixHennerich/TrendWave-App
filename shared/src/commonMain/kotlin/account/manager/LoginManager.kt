package account.manager

import account.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import managers.DataStorageManager
import managers.HTTPManager
import managers.exceptions.ExceptionHandler
import managers.exceptions.NException
import utilities.EncryptionUtil

class LoginManager {

    /**
     * Method called when user tries to login within the app
     *
     * @param email -> of account
     * @param password -> not encrypted
     * @return -> Error Handeling
     */
    suspend fun login(email: String, password: String): NException{
        val httpManager = HTTPManager()

        val encryptedPassword = EncryptionUtil.encryption(password)
        val passwordDB = httpManager.getValue(
            "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/connectGet.php",
            "password",
            null,
            email
        )
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
    suspend fun isLoggedIn(localDataManager: DataStorageManager): Boolean{
        if(localDataManager.readString("email") != null &&
            localDataManager.readString("password") != null &&
            localDataManager.readString("username") != null) {
            val loginManager = LoginManager()
            val exceptionHandler = ExceptionHandler()
            val message = exceptionHandler.fetchErrorMessage(
                loginManager.login(
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