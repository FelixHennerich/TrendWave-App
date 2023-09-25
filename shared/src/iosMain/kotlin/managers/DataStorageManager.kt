package managers

import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue
import utilities.CommonLogger

actual class DataStorageManager {

    private val userDefaults = NSUserDefaults.standardUserDefaults


    /**
     * Save String on the local device storage
     *
     * @param key -> What should be saved?
     * @param value -> This value will be assigned
     * @sample key = "email"
     * @sample value = "test@test.de"
     */
    actual fun saveString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize()

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
        val storedValue = userDefaults.objectForKey(key)
        return if(storedValue.toString() == "null"){
            null
        }else {
            storedValue.toString()
        }
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
        userDefaults.setInteger(value, forKey = key)
        userDefaults.synchronize()

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
        return userDefaults.integerForKey(key).toInt()
    }

    /**
     * Delete Value from local data storage
     *
     *  @param key -> What sould be deleted?
     *  @sample key = "email"
     */
    actual fun deleteEntry(key: String) {
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }
}