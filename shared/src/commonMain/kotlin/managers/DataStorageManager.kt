package managers

expect class DataStorageManager {

    fun saveString(key: String, value: String)
    fun readString(key: String): String?
    fun saveInt(key: String, value: Long)

    fun readInt(key: String, defaultValue: Int): Int

    fun deleteEntry(key: String)

}