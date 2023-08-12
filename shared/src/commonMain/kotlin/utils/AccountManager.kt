package utils

import io.ktor.util.date.GMTDate

class AccountManager {

    /**
     * Make sure that every information is correct before using this method.
     *
     * No multiple username
     * No wrong Passwords
     * etc.
     */
    suspend fun createAccount(email: String, password: String, username: String, birthday: String){
        val role = "Member" // nerver create a account with owner permissions
        val encryptedPassword = EncryptionManager.encryption(password);
        HTTPManager().postInsert("https://cross-cultural-auto.000webhostapp.com/php/connectInsert.php", "newsuser", "uuid, email, password, username, birthday, signup, role", "UUID,$email,$encryptedPassword,$username,$birthday,${getCurrentDate()},$role").toString()
    }

    /*fun generateUniqueIdUsingTime(): String {
        val currentTimeMillis = Date
        return currentTimeMillis.toString()
    }*/

    fun getCurrentDate(): String {
        val currentDate = GMTDate()
        return currentDate.toString()
    }
}