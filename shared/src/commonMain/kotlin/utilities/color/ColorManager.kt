package utilities.color

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromEnum(color: Colors): Color {
    return when (color) {
        Colors.PRIMARY -> Color(red = 6, green = 20, blue = 27)
        Colors.SECONDARY-> Color(red = 17, green = 33, blue = 45)
        Colors.TERTIARY -> Color(red = 37, green = 55, blue = 69)
        Colors.QUATERNARY -> Color(red = 74, green = 92, blue = 106)
        Colors.QUINARY -> Color(red = 155, green = 168, blue = 171)
        Colors.SENARY -> Color(red = 204, green = 208, blue = 207)
        else -> Color(red = 255,green = 255,blue = 255)
    }
}