package views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import utilities.presentation.BottomSheet

class TestView {

    @Composable
    fun testView() {
        BottomSheet(
            visible = true,
            modifier = Modifier,
            backgroundcolor = Color.White,
        ){
            Text("Abcd")
            Spacer(Modifier.height(16.dp))
            Icon(Icons.Rounded.AccountBox, contentDescription = null)
        }
    }
}