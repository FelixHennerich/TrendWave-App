package account

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import utilities.CommonLogger

class RESTfulUserManager {

    private val client = HttpClient()
    private val url = "https://felix.henneri.ch/php/RESTfulAPI/"

    data class User(
        val uuid: String,
        val email: String,
        val password: String,
        val username: String,
        val role: String,
        val birthday: String,
        val signup: String,
        val follower: String,
        var following: String,
        var followed: String,
        var homebuttons: String
    )

    /**
     * Find a single Post by its id
     *
     * @param id -> Post unique ID
     * @return finished post
     */
    suspend fun findUserByUUID(uuid: String): User {
        val finurl = url + "userGetter.php"
        val response = client.get(finurl) {
            url {
                parameters.append("uuid", uuid)
            }
        }
        val entryLists = jsonStringToEntryLists(response.bodyAsText())

        return entryLists[0]
    }

    /**
     * Find a single Post by its email
     *
     * @param email -> unique email
     * @return finished user
     */
    suspend fun findUserByEmail(email: String): User {
        val finurl = url + "userGetter.php"
        val response = client.get(finurl) {
            url {
                parameters.append("email", email)
            }
        }

        val entryLists = jsonStringToEntryLists(response.bodyAsText())

        return entryLists[0]
    }

    /**
     * Find a single Post by its username
     *
     * @param username -> username
     * @return finished user
     */
    suspend fun findUserByUsername(username: String): User {
        val commonLogger = CommonLogger()
        val finurl = url + "userGetter.php"
        val response = client.get(finurl) {
            url {
                parameters.append("username", username)
            }
        }

        val entryLists = jsonStringToEntryLists(response.bodyAsText())

        return entryLists[0]
    }

    /**
     * Delete specific user by id
     *
     * @param uuid -> user unique id
     */
    suspend fun deleteUser(uuid: String){
        val finurl = url + "userGetter.php"
        client.get(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("delete", "true")
            }
        }
    }

    /**
     * Upload user to database
     *
     * @param uuid -> Unique ID
     * @param email -> Email address
     * @param username -> Unique Username
     * @param password -> Encrypted password
     * @param signup -> Day of singup
     * @param birthday -> Birthday
     * @param role -> Role of user. DO NEVER CREATE A USER WITH ADMIN BY DEFAULT
     * @return finished User
     */
    suspend fun uploadUser(uuid: String, email: String, username: String, password: String, signup: String, birthday: String, role: String): User{
        val finurl = url + "userGetter.php"

        val response = client.post(finurl) {
            url {
                parameters.append("uuid", uuid)
                parameters.append("email", email)
                parameters.append("username", username)
                parameters.append("password", password)
                parameters.append("signup", signup)
                parameters.append("birthday", birthday)
                parameters.append("role", role)
            }
        }

        return User(uuid,email,password,username,role,birthday,signup,"0","0", "", "")
    }

    suspend fun usernameCheck(username: String): Boolean{
        val finurl = url + "userGetter.php"

        val response = client.post(finurl) {
            url {
                parameters.append("username", username)
                parameters.append("value", "1")
            }
        }
        return response.bodyAsText().contains("Username is free")
    }

    suspend fun emailCheck(email: String): Boolean{
        val finurl = url + "userGetter.php"

        val response = client.post(finurl) {
            url {
                parameters.append("email", email)
                parameters.append("value", "1")
            }
        }
        return response.bodyAsText().contains("Email is free")
    }

    /**
     * Convert json to list
     *
     * @param jsonString -> String of all content
     * @return list of user
     */
    private fun jsonStringToEntryLists(jsonString: String): List<User> {
        val entryLists = mutableListOf<User>()
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
                entryLists.add(User(partlst[0], partlst[1], partlst[2], partlst[3], partlst[6], partlst[4], partlst[5], partlst[7], partlst[8], partlst[9], partlst[10]))
            }
        }

        return entryLists
    }

}