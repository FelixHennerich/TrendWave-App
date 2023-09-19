package managers

class DataStorageOnLogin(
    private val localDataManager: DataStorageManager
) {

    fun storeData(user: String, password: String, username: String, role: String, uuid: String){
        localDataManager.saveString("email", user)
        localDataManager.saveString("password", password)
        localDataManager.saveString("username", username)
        localDataManager.saveString("role", role)
        localDataManager.saveString("uuid", uuid)
    }
}