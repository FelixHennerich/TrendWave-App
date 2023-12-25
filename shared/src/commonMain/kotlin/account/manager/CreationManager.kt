package account.manager

import account.RESTfulUserManager
import kotlinx.coroutines.delay
import utilities.textutils.DateUtil
import utilities.textutils.EncryptionUtil
import managers.exceptions.NException

class CreationManager {

    val restAPI = RESTfulUserManager()

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
    suspend fun createAccount(email: String, uuid: String, password: String, username: String, birthday: String): NException {
        delay(1000)

        if(!charChecker(email) || !charChecker(password) || !charChecker(username)){
            return NException.UnallowedCharacters105 // Unauthorized characters
        }
        if(!checkEmail(email)) {
            return NException.Emailwrong100 // EMAIL DOES NOT CONTAIN @ OR . -> WRONG EMAIL
        }
        if(email.length < 8) {
            return NException.PasswordToWeak101 // Password to weak
        }
        if(username.length < 5 || username.length > 32) {
            return NException.UsernameLength102 // Username too short/long
        }
        if(!userNameExists(username)) {
            return NException.UsernameExists103 // Username already exists
        }
        if(!emailExists(email)){
            return NException.EmailExists104 // Username already exists
        }
        if(!birhtdayChecker(birthday)){
            return NException.BirthdayWrong106 // Birthday wrong dd.mm.yyyy
        }

        val role = "Member" // IMPOmRTANT nerver create a account with owner permissions by default
        val encryptedPassword = EncryptionUtil.encryption(password); // Password encryption
        val dateUtil = DateUtil() // Current date util

        try {
            restAPI.uploadUser(uuid,email, username, encryptedPassword, dateUtil.getCurrentDate(), birthday, role)
            return NException.SUCCESS001
        }catch (e: Exception){
            e.printStackTrace()
        }
        return NException.DatabaseCreation401 //Error while creating account in datebase*/
    }

    /**
     * Checks for username whether it already exists
     *
     * @param username -> Username to check
     * @return -> Exists = true; No Exists = false
     */
    suspend fun userNameExists(username: String): Boolean{
        return restAPI.usernameCheck(username)
    }

    /**
     * Checks for email whether it already exists
     *
     * @param email -> email to check
     * @return -> Exists = false; No Exists = true
     */
    suspend fun emailExists(email: String): Boolean{
        return restAPI.emailCheck(email)
    }

    /**
     * Check for validity of email (@ and .)
     *
     * @param email -> Email to Check
     * @return -> Contains = true; Not = false
     */
    fun checkEmail(email: String): Boolean{
        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if(emailRegex.matches(email)) {
            return true
        }
        return true
    }

    /**
     * Check for validity of email
     *
     * @param birthday -> Birthday to check
     * @return true or false
     */
    fun birhtdayChecker(birthday: String): Boolean{
        val regex = Regex("\\d{2}.\\d{2}.\\d{4}")
        if(regex.matches(birthday)){
            return true
        }
        return false
    }

    /**
     * Check whether a character is allowed in this context or not
     *
     * @param value -> String that has to be checked
     * @return -> true = string is ok, false = string isnt ok
     */
    fun charChecker(value: String): Boolean{
        val acceptedCharset = mutableListOf("a","b","c","d","e","f","g","'",">","@","~",
            "h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","€","ü",
            "y","z","1","2","3","4","5","6","7","8","9","0","!","§","$","%","&","ä","ö",
            "/","(",")","=","?","ß","*","+","`","´","-","_",".",":",",",";","<"," ")
        for(i in value.indices){
            if(!acceptedCharset.contains(value[i].toString().lowercase())){
                println(value[i])
                return false
            }
        }
        return true
    }

}