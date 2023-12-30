package post

import account.AppUser
import account.utilities.UUID
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import post.presentation.Thema
import utilities.CommonLogger
import utilities.textutils.DateUtil

class RESTfulPostManager{

    private val client = HttpClient()
    private val url = "https://felix.henneri.ch/php/RESTfulAPI/"


    /**
     * Find a single Post by its id
     *
     * @param id -> Post unique ID
     * @return finished post
     */
    suspend fun findPostById(id: String): Post {
        val finurl = url + "postGetter.php"
        val response = client.get(finurl) {
            url {
                parameters.append("id", id)
            }
        }


        val commonLogger = CommonLogger()
        commonLogger.log(response.bodyAsText())


        val entryLists = jsonStringToEntryLists(response.bodyAsText())

        commonLogger.log(entryLists.toString())

        return entryLists[0]
    }

    /**
     * Get 10-20 Random Posts of all users
     *
     * @return List of posts
     */
    suspend fun getRandomPosts(): List<Post> {
        val finurl = url + "postGetter.php"
        val response = client.get(finurl) {
            url {}
        }
        return jsonStringToEntryLists(response.bodyAsText())
    }

    /**
     * Get all posts of one specific user
     *
     * @param uuid -> unique user id
     * @return list of all posts
     */
    suspend fun getUserPosts(uuid: String): List<Post> {
        val finurl = url + "postGetter.php"
        val response = client.get(finurl) {
            url {
                parameters.append("uuid", uuid)
            }
        }
        return jsonStringToEntryLists(response.bodyAsText())
    }

    /**
     * Delete specific post by id
     *
     * @param id -> Post unique id
     */
    suspend fun deletePost(id: String){
        val finurl = url + "postGetter.php"
        client.get(finurl) {
            url {
                parameters.append("id", id)
                parameters.append("delete", "true")
            }
        }
    }

    /**
     * Upload post to database
     *
     * @param uuid -> Player id
     * @param text -> Post content text
     * @return finished Post
     */
    suspend fun uploadPost(uuid: String, text: String, theme: String): Post{
        val finurl = url + "postGetter.php"
        val postidmanager = UUID()
        val id = postidmanager.generate128BitUUID()
        val dateUtil = DateUtil()
        val date = dateUtil.getCurrentDate()

        client.post(finurl) {
            url {
                parameters.append("id", id)
                parameters.append("uuid", uuid)
                parameters.append("date", date)
                parameters.append("text", text)
                parameters.append("theme", theme)
            }
        }

        val user = AppUser()
        val username = user.getUsername(uuid)

        return Post(id,uuid,username,date,text, theme)
    }

    /**
     * Convert json to list
     *
     * @param jsonString -> String of all content
     * @return list of posts
     */
    suspend fun jsonStringToEntryLists(jsonString: String): List<Post> {
        val entryLists = mutableListOf<Post>()
        val user = AppUser()
        val cleanedJsonString = jsonString.trim().removePrefix("[").removeSuffix("]")
        val jsonObjects = cleanedJsonString.split("{")

        for (jsonObject in jsonObjects) {
            if (jsonObject.isNotBlank()) {
                val keyValuePairs = jsonObject.trim().removeSuffix(",").removeSuffix("}").split(",")
                val partlst = mutableListOf<String>()

                for (pair in keyValuePairs) {
                    val parts = pair.split(":")
                    partlst.add(parts[1].removePrefix("\"").removeSuffix("\""))
                }
                entryLists.add(Post(partlst[0], partlst[1], user.getUsername(partlst[1]),partlst[2], partlst[3], partlst[4]))
            }
        }

        return entryLists
    }


}
