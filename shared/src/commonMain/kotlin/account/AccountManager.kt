package account

import io.ktor.util.date.GMTDate
import utils.EncryptionManager
import utils.HTTPManager
import utils.MonthCalc

class AccountManager {

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
     *
     * @throws 1 -> Account successfully created
     * @throws 100 -> Email does not contain @ or . -> wrong email
     * @throws 101 -> password to weak
     * @throws 102 -> username to short / long
     * @throws 103 -> username already esists
     * @throws 400 -> HTTP Error while posting
     * @throws 401 -> Error while creating account in database
     */
    suspend fun createAccount(email: String, password: String, username: String, birthday: String, authcode: String): Int {
        if(!checkEmail(email))
            return 100 // EMAIL DOES NOT CONTAIN @ OR . -> WRONG EMAIL
        if(email.length < 8)
            return 101 // Password to weak
        if(username.length < 5 || username.length > 32)
            return 102 // Username too short/long
        if(userNameExists(username))
            return 103 // Username already exists

        val role = "Member" // IMPORTANT nerver create a account with owner permissions by default
        val encryptedPassword = EncryptionManager.encryption(password); // Password encryption
        val uuidclass = UUID() // uuid class
        val uuid = uuidclass.generate128BitUUID() // 128Bit uuid generation

        try {
            if(HTTPManager().postInsert(
                "https://cross-cultural-auto.000webhostapp.com/php/connectInsert.php",
                "newsuser",
                    uuid, email, username,
                    encryptedPassword, getCurrentDate(), birthday,
                    role,authcode
            ).toString().contains("200 OK")) {
                return 1 // Account successfully created
            }else {
                return 400 // HTTP Error while posting
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return 401 //Error while creating account in datebase
    }

    /**
     * Checks for username whether it already exists
     *
     * @param username -> Username to check
     * @return -> Exists = true; No Exists = false
     */
    fun userNameExists(username: String): Boolean{
        return false
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

    /**
     * Takes the current date of the day
     *
     * @return Date of Day
     * @sample (dd.mm.yyyy)
     */
    fun getCurrentDate(): String {
        val day = GMTDate().dayOfMonth
        val month = MonthCalc.numberOfMonth(GMTDate().month.toString())
        val year = GMTDate().year
        val date = "$day.$month.$year"
        return date
    }
}