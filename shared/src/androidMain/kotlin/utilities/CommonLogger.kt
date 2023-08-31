package utilities

actual class CommonLogger actual constructor() {
    actual fun log(message: String) {
        android.util.Log.d("LOG-MESSAGE", message)
    }
}