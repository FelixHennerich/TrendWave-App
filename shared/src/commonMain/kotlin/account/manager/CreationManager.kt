package account.manager

import account.utilities.UUID
import kotlinx.coroutines.delay
import utilities.DateUtil
import utilities.EncryptionUtil
import managers.HTTPManager
import managers.exceptions.NException
import kotlin.native.concurrent.ThreadLocal

class CreationManager {
    
    /**
     * Function to check account data and ready up the account creation
     *
     * Make sure that every information is correct before using this method.
     *
     * @param email -> Email of User
     * @param password -> Not encrypted password  PASSWORDS MUST CONFIRM SOME STANDARDS
     * @param username -> username the user wants  MULTIPLE USERNAMES ARE NOT ALLOWED
     * @param birthday -> Birthday date of User
     * @param authcode -> Authcode in MySQL MUST BE CHANGED AFTER USE
     * @return -> Error/Success-Code -- Following Codes
     */
    suspend fun createAccount(email: String, password: String, username: String, birthday: String): NException {
        val authcodemanager = AuthCodeManager()
        val authcode: String = authcodemanager.getNewAuthcode()
        delay(1000)

        if(!checkEmail(email)) {
            authcodemanager.deactivateAuthcode(authcode)
            return NException.Emailwrong100 // EMAIL DOES NOT CONTAIN @ OR . -> WRONG EMAIL
        }
        if(email.length < 8) {
            authcodemanager.deactivateAuthcode(authcode)
            return NException.PasswordToWeak101 // Password to weak
        }
        if(username.length < 5 || username.length > 32) {
            authcodemanager.deactivateAuthcode(authcode)
            return NException.UsernameLength102 // Username too short/long
        }
        if(userNameExists(username, authcode)) {
            authcodemanager.deactivateAuthcode(authcode)
            return NException.UsernameExists103 // Username already exists
        }

        val role = "Member" // IMPOmRTANT nerver create a account with owner permissions by default
        val encryptedPassword = EncryptionUtil.encryption(password); // Password encryption
        val uuidclass = UUID() // uuid class
        val uuid = uuidclass.generate128BitUUID() // 128Bit uuid generation
        val dateUtil = DateUtil() // Current date util

        try {
            if(HTTPManager().postInsert(
                "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/connectInsert.php",
                "newsuser",
                    uuid, email, username,
                    encryptedPassword, dateUtil.getCurrentDate(), birthday,
                    role,authcode
            ).toString().contains("200 OK")) {
                authcodemanager.deactivateAuthcode(authcode)
                return NException.SUCCESS001 // Account successfully created
            }else {
                authcodemanager.deactivateAuthcode(authcode)
                return NException.HTTPPosting400 // HTTP Error while posting
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        authcodemanager.deactivateAuthcode(authcode)
        return NException.DatabaseCreation401 //Error while creating account in datebase*/
    }

    /**
     * Checks for username whether it already exists
     *
     * @param username -> Username to check
     * @return -> Exists = true; No Exists = false
     */
    suspend fun userNameExists(username: String, authcode: String): Boolean{
        try{
            if(HTTPManager().usernameCheck(
                    "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/checkUsername.php",
                    "newsuser",
                    username,
                    authcode).contains("Username is free"))
                return false
        } catch (e: Exception){
            e.printStackTrace()
            return true
        }
        return true
    }

    /**
     * Check for validity of email (@ and .)
     *
     * @param email -> Email to Check
     * @return -> Contains = true; Not = false
     */
    fun checkEmail(email: String): Boolean{
        return email.contains("@") && email.contains(".")
    }

}