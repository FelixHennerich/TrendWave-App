package account

import account.manager.AuthCodeManager
import account.utilities.RoleType
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import managers.HTTPManager

class User : UserInterface {

    private val url: String
        get() = "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/connectGet.php"
    val httpManager = HTTPManager()

    override suspend fun getEmail(uuid: String): String {
        val email = httpManager.getValue(url, "email", uuid)
        return email ?: "No Email Found Error"
    }

    override fun getUsername(uuid: String): String {
        TODO("Not yet implemented")
    }

    override fun getUUID(email: String): String {
        TODO("Not yet implemented")
    }

    override fun getRole(uuid: String): RoleType {
        TODO("Not yet implemented")
    }

    override fun getBirthday(uuid: String): String {
        TODO("Not yet implemented")
    }

    override fun getSignupday(uuid: String): String {
        TODO("Not yet implemented")
    }

    override fun hasPermissionRole(role: RoleType): Boolean {
        TODO("Not yet implemented")
    }

}