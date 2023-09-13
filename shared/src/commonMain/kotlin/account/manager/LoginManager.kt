package account.manager

import account.User
import managers.HTTPManager
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

}