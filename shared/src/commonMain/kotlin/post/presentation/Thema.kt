package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.ChevronUp

/**
 * Theme enum for posts
 *
 * @param displayName -> name of theme
 */
enum class Thema(val displayName: String) {
    THEMA0("Select your topic..."),
    THEMA1("Computer Science"),
    THEMA2("Social Media"),
    THEMA3("Animals"),
}

/**
 * Dropdownmenu in the post screen
 *
 * @param mainbackgroundcolor -> background
 * @param secondbackgroundcolor -> background of boxes
 * @param texticoncolor -> Color of text and icons
 * @param bordercolor -> color of box border
 * @param corner -> corner shape
 * @param cornerrad -> corner radius
 * @return selected theme
 */
@Composable
fun DropdownMenu(
    mainbackgroundcolor: Color,
    secondbackgroundcolor: Color,
    texticoncolor: Color,
    bordercolor: Color,
    corner: RoundedCornerShape,
    cornerrad: Dp
) : Thema {
    var expanded by remember { mutableStateOf(false) } // is dropdownmenu toggled?
    var selectedThema by remember { mutableStateOf(Thema.THEMA0) } // currently selected theme
    var cornerstart by remember { mutableStateOf(cornerrad) } // Radius of corner
    var cornerend by remember { mutableStateOf(cornerrad) } // Radius of corner
    //If toggled change
    if(expanded){
        cornerstart = 0.dp
        cornerend = 0.dp
    }else {
        cornerend = cornerrad
        cornerstart = cornerrad
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(
                color = mainbackgroundcolor,
                shape = corner
            )
            .clickable { expanded = !expanded }
            .border(
                width = 1.dp,
                color = bordercolor,
                shape = corner
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = bordercolor,
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = cornerstart,
                        bottomEnd = cornerend,
                    )
                )
                .padding(8.dp)

        ) {
            Text(
                text = selectedThema.displayName,
                color = texticoncolor,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) FeatherIcons.ChevronUp  else FeatherIcons.ChevronDown,
                tint = texticoncolor,
                contentDescription = null
            )
        }

        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .background(secondbackgroundcolor)
            ) {
                Column {
                    Thema.values().forEach { theme ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    selectedThema = theme
                                    expanded = false
                                }
                        ) {
                            Text(
                                text = theme.displayName,
                                color = texticoncolor
                            )
                        }
                    }
                }
            }
        }
    }
    return selectedThema
}
