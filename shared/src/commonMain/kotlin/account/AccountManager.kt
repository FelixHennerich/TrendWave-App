package account

import io.ktor.util.date.GMTDate
import utils.EncryptionManager
import utils.HTTPManager
import utils.MonthCalc

class AccountManager {

    /**
     * Make sure that every information is correct before using this method.
     *
     * No multiple username
     * No wrong Passwords
     * etc.
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

    fun userNameExists(username: String): Boolean{
        return false
    }
    fun checkEmail(email: String): Boolean{
        return email.contains("@") && email.contains(".")
    }

    fun getCurrentDate(): String {
        val day = GMTDate().dayOfMonth
        val month = MonthCalc.numberOfMonth(GMTDate().month.toString())
        val year = GMTDate().year
        val date = "$day.$month.$year"
        return date
    }
}