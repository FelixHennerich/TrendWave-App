package post

import account.User
import account.manager.AuthCodeManager
import account.utilities.UUID
import event.TrendWaveState
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import utilities.CommonLogger
import utilities.DateUtil

class PostLoader {

    private val client = HttpClient()
    val authCodeManager = AuthCodeManager()


    /**
     * Load a list posts from the database
     *
     * @return List<Post> 20-50 needed for fluent experience
     */
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

        val idlst = mutableListOf<String>()
        val textlst = mutableListOf<String>()
        val uuidlst = mutableListOf<String>()
        val datelst = mutableListOf<String>()

        lst.forEach { entry ->
            when {
                entry.startsWith("IDDD") -> idlst.add(entry.substring(4))
                entry.startsWith("TEXT") -> textlst.add(entry.substring(4))
                entry.startsWith("UUID") -> uuidlst.add(entry.substring(4))
                entry.startsWith("DATE") -> datelst.add(entry.substring(4))
            }
        }

        val namelst = uuidlst.map { user.getUsername(it) }

        while (namelst.size < uuidlst.size) {
            delay(1)
        }

        val postlst = idlst.indices.map { i ->
            Post(
                id = idlst[i],
                uuid = uuidlst[i],
                username = namelst[i],
                date = datelst[i],
                text = textlst[i]
            )
        }


        return postlst
    }

    /**
     * Load a list posts from the database
     *
     * @param uuid -> Unique user ID
     * @return List<Post> 20-50 needed for fluent experience
     */
    suspend fun loadUserPosts(uuid: String): List<Post>{
        val url = "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/postGetUser.php"
        val authcode = authCodeManager.getNewAuthcode()
        val user = User()

        val response = client.get(url) {
            url{
                parameters.append("uuid", uuid)
                parameters.append("authcodetocheck", authcode)
            }
        }
        authCodeManager.deactivateAuthcode(authcode)

        val lst = response.bodyAsText().split("#")

        val idlst = mutableListOf<String>()
        val textlst = mutableListOf<String>()
        val uuidlst = mutableListOf<String>()
        val datelst = mutableListOf<String>()

        lst.forEach { entry ->
            when {
                entry.startsWith("IDDD") -> idlst.add(entry.substring(4))
                entry.startsWith("TEXT") -> textlst.add(entry.substring(4))
                entry.startsWith("UUID") -> uuidlst.add(entry.substring(4))
                entry.startsWith("DATE") -> datelst.add(entry.substring(4))
            }
        }

        val namelst = uuidlst.map { user.getUsername(it) }

        while (namelst.size < uuidlst.size) {
            delay(1)
        }

        val postlst = idlst.indices.map { i ->
            Post(
                id = idlst[i],
                uuid = uuidlst[i],
                username = namelst[i],
                date = datelst[i],
                text = textlst[i]
            )
        }

        val commonLogger = CommonLogger()
        commonLogger.log(postlst.toString())


        return postlst
    }

    /**
     * Upload a new created post
     *
     * @param uuid -> player unique id
     * @param text -> content of the post
     */
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

    /**
     * Delete post from the database
     *
     * @param id -> Unique post id
     */
    suspend fun deletePost(id: String){
        val authcode = authCodeManager.getNewAuthcode()
        val url = "https://cross-cultural-auto.000webhostapp.com/php/MySQLBridge/postDelete.php"

        client.get(url) {
            url {
                parameters.append("id", id)
                parameters.append("authcodetocheck", authcode)
            }
        }

        authCodeManager.deactivateAuthcode(authcode)
    }

}