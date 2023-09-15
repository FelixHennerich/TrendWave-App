package post

import account.User
import account.manager.AuthCodeManager
import account.utilities.UUID
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import utilities.CommonLogger
import utilities.DateUtil

class PostLoader {

    private val client = HttpClient()
    val authCodeManager = AuthCodeManager()

    suspend fun loadPost(): List<Post>{
        val url = "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/postGet.php"
        val authcode = authCodeManager.getNewAuthcode()
        val user = User()

        val response = client.get(url) {
            url{
                parameters.append("authcodetocheck", authcode)
            }
        }
        authCodeManager.deactivateAuthcode(authcode)

        val lst = response.bodyAsText().split("#")

        var idlst = mutableListOf<String>()
        var textlst = mutableListOf<String>()
        var uuidlst = mutableListOf<String>()
        var datelst = mutableListOf<String>()

        for(entry in lst){
            if(entry.startsWith("IDDD")){
                val newentry = entry.substring(4)
                idlst.add(newentry)
            }
            if(entry.startsWith("TEXT")){
                val newentry = entry.substring(4)
                textlst.add(newentry)
            }
            if(entry.startsWith("UUID")){
                val newentry = entry.substring(4)
                uuidlst.add(newentry)
            }
            if(entry.startsWith("DATE")){
                val newentry = entry.substring(4)
                datelst.add(newentry)
            }
        }

        var namelst = mutableListOf<String>()

        for(entry in uuidlst){
            namelst.add(user.getUsername(entry))
        }

        while(namelst.size < uuidlst.size){
            delay(1)
        }

        var postlst = mutableListOf<Post>()

        for(i in 0 until idlst.size){
            postlst.add(Post(
                id = idlst[i],
                uuid = uuidlst[i],
                username = namelst[i],
                date = datelst[i],
                text = textlst[i]
            ))
        }


        return postlst
    }

    suspend fun uploadPost(uuid: String, text: String){
        val authcode = authCodeManager.getNewAuthcode()
        val url = "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/postInsert.php"
        val dateUtil = DateUtil()
        val postidmanager = UUID()
        val id = postidmanager.generate128BitUUID()
        val date = dateUtil.getCurrentDate()

        client.get(url) {
            url {
                parameters.append("id", id)
                parameters.append("uuid", uuid)
                parameters.append("date", date)
                parameters.append("text", text)
                parameters.append("authcodetocheck", authcode)
            }
        }

        authCodeManager.deactivateAuthcode(authcode)
    }

}