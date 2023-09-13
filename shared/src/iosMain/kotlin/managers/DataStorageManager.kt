package managers

import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

actual class DataStorageManager {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun saveString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize()

    }

    actual fun readString(key: String): String? {
        return userDefaults.objectForKey(key) as? String
    }

    actual fun saveInt(key: String, value: Long) {
        userDefaults.setInteger(value, forKey = key)
        userDefaults.synchronize()

    }

    actual fun readInt(key: String, defaultValue: Int): Int {
        return userDefaults.integerForKey(key).toInt()
    }

    actual fun deleteEntry(key: String) {
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }
}