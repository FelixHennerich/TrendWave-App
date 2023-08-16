package account.manager

import account.utilities.UUID
import utilities.DateUtil
import utilities.EncryptionUtil
import managers.HTTPManager
import managers.exceptions.NException

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
    suspend fun createAccount(email: String, password: String, username: String, birthday: String, authcode: String): NException {
        if(!checkEmail(email))
            return NException.Emailwrong100 // EMAIL DOES NOT CONTAIN @ OR . -> WRONG EMAIL
        if(email.length < 8)
            return NException.PasswordToWeak101 // Password to weak
        if(username.length < 5 || username.length > 32)
            return NException.UsernameLength102 // Username too short/long
        if(userNameExists(username, authcode))
            return NException.UsernameExists103 // Username already exists

        val role = "Member" // IMPOmRTANT nerver create a account with owner permissions by default
        val encryptedPassword = EncryptionUtil.encryption(password); // Password encryption
        val uuidclass = UUID() // uuid class
        val uuid = uuidclass.generate128BitUUID() // 128Bit uuid generation
        val dateUtil = DateUtil() // Current date util

        try {
            if(HTTPManager().postInsert(
                "https://cross-cultural-auto.000webhostapp.com/php/connectInsert.php",
                "newsuser",
                    uuid, email, username,
                    encryptedPassword, dateUtil.getCurrentDate(), birthday,
                    role,authcode
            ).toString().contains("200 OK"))
                return NException.SUCCESS001 // Account successfully created
            else
                return NException.HTTPPosting400 // HTTP Error while posting
        }catch (e: Exception){
            e.printStackTrace()
        }
        return NException.DatabaseCreation401 //Error while creating account in datebase
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
                    "https://cross-cultural-auto.000webhostapp.com/php/checkUsername.php",
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