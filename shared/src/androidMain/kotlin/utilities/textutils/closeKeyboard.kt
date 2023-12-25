package utilities.textutils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun closeKeyboard(){
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    softwareKeyboardController?.hide()
}