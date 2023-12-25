package utilities.textutils

import platform.UIKit.UIApplication
import platform.UIKit.endEditing

actual fun closeKeyboard() {
    val keyWindow = UIApplication.sharedApplication.keyWindow
    keyWindow?.endEditing(true)
}
