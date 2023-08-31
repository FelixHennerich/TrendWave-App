package utilities

import android.util.Log

actual class CommonLogger actual constructor() {
    actual fun log(message: String) {
        android.util.Log.d("START", "--------------------------")
        android.util.Log.d("MESSAGE", message)
        android.util.Log.d("END", "--------------------------")
        Log.d("-"," ")
    }
}