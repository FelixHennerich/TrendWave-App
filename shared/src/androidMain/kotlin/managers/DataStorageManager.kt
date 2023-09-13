package managers

import android.content.Context
import android.content.SharedPreferences

actual class DataStorageManager(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TrendWave", Context.MODE_PRIVATE)

    actual fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    actual fun readString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    actual fun saveInt(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value.toInt())
        editor.apply()
    }

    actual fun readInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    actual fun deleteEntry(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}