package managers

import android.content.Context
import android.content.SharedPreferences

actual class DataStorageManager(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TrendWave", Context.MODE_PRIVATE)


    /**
     * Save String on the local device storage
     *
     * @param key -> What should be saved?
     * @param value -> This value will be assigned
     * @sample key = "email"
     * @sample value = "test@test.de"
     */
    actual fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Read String from the local device storage
     *
     * @param key -> What should be read?
     * @sample key = "email"
     * @return value -> the assigned value to the key
     * @sample value = "test@test.de"
     */
    actual fun readString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    /**
     * Save Int on the local device storage
     *
     * @param key -> What should be saved?
     * @param value -> This value will be assigned
     * @sample key = "age"
     * @sample value = "23"
     */
    actual fun saveInt(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value.toInt())
        editor.apply()
    }

    /**
     * Read Int from the local device storage
     *
     * @param key -> What should be read?
     * @sample key = "age"
     * @return value -> the assigned value to the key
     * @sample value = "23"
     */
    actual fun readInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    /**
     * Delete Value from local data storage
     *
     *  @param key -> What sould be deleted?
     *  @sample key = "email"
     */
    actual fun deleteEntry(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}