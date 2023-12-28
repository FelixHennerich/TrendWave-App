package utilities.color

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromEnum(color: Colors): Color {
    return when (color) {
        //Dark blue -> Gray
        Colors.PRIMARY -> Color(red = 6, green = 20, blue = 27)
        Colors.SECONDARY-> Color(red = 17, green = 33, blue = 45)
        Colors.TERTIARY -> Color(red = 37, green = 55, blue = 69)
        Colors.QUATERNARY -> Color(red = 74, green = 92, blue = 106)
        Colors.QUINARY -> Color(red = 155, green = 168, blue = 171)
        Colors.SENARY -> Color(red = 204, green = 208, blue = 207)


        /*Colors.PRIMARY -> Color(red = 3, green = 32, blue = 48)
        Colors.SECONDARY-> Color(red = 2, green = 43, blue = 66)
        Colors.TERTIARY -> Color(red = 0, green = 53, blue = 84)
        Colors.QUATERNARY -> Color(red = 0, green = 77, blue = 116)
        Colors.QUINARY -> Color(red = 0, green = 100, blue = 148)
        Colors.SENARY -> Color(red = 204, green = 208, blue = 207)*/

        /*Colors.PRIMARY -> Color(red = 16, green = 9, blue = 29)
        Colors.SECONDARY-> Color(red = 40, green = 25, blue = 61)
        Colors.TERTIARY -> Color(red = 70, green = 49, blue = 92)
        Colors.QUATERNARY -> Color(red = 104, green = 80, blue = 123)
        Colors.QUINARY -> Color(red = 141, green = 118, blue = 154)
        Colors.SENARY -> Color(red = 217, green = 216, blue = 217)*/

        /*Colors.PRIMARY -> Color(red = 28, green = 28, blue = 28)
        Colors.SECONDARY-> Color(red = 218, green = 221, blue = 216)
        Colors.TERTIARY -> Color(red = 236, green = 235, blue = 228)
        Colors.QUATERNARY -> Color(red = 238, green = 240, blue = 242)
        Colors.QUINARY -> Color(red = 214, green = 232, blue = 238)
        Colors.SENARY -> Color(red = 217, green = 216, blue = 217)*/


        Colors.GREEN -> Color(red = 50, green = 168, blue = 70)
        Colors.HIGHLIGHT -> Color(red = 217, green = 216, blue = 217)
        else -> Color(red = 255,green = 255,blue = 255)
    }
}