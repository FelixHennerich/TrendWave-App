package post.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
    THEMA2("Travel Advent"),
    THEMA3("Photo"),
    THEMA4("Creativity"),
    THEMA5("Health Wellness"),
    THEMA6("Fashion Style"),
    THEMA7("Tech Innovate"),
    THEMA8("Music Concerts"),
    THEMA9("Film TV"),
    THEMA10("Sports Fit"),
    THEMA11("Food Cook"),
    THEMA12("Animals"),
    THEMA13("Environment"),
    THEMA14("Research"),
    THEMA15("Books"),
    THEMA16("DIY Crafts"),
    THEMA17("Gaming"),
    THEMA18("Discoveries"),
    THEMA19("Cars Bikes"),
    THEMA20("Culture"),
    THEMA21("Comedy"),
    THEMA22("PhotoEdit"),
    THEMA23("Education"),
    THEMA24("Social Justice"),
    THEMA25("Psychology"),
    THEMA26("Spirit Develop"),
    THEMA27("Career"),
    THEMA28("Family"),
    THEMA29("Outdoors"),
    THEMA30("Mindful"),
    THEMA31("Art"),
    THEMA32("Recipes"),
    THEMA33("Tattoos"),
    THEMA34("MusicProd"),
    THEMA35("News Events"),
    THEMA36("Sports"),
    THEMA37("Gardening"),
    THEMA38("Astrology"),
    THEMA39("MotorTech"),
    THEMA40("LGBTQ+"),
    THEMA41("HairBeauty"),
    THEMA42("ProfDevTips"),
    THEMA43("Yoga"),
    THEMA44("LangCulture"),
    THEMA45("ParentUpbring"),
    THEMA46("TechTrends"),
    THEMA47("AstroHoros"),
    THEMA48("VR AR"),
    THEMA49("Nightlife Events"),
    THEMA50("Self Defense"),
    THEMA51("Philosophy Ideas")

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

    // Animation for corner radius
    val cornerAnim = animateDpAsState(
        targetValue = if (expanded) 0.dp else cornerrad,
        animationSpec = tween(durationMillis = 50)
    )

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
                        topStart = cornerrad,
                        topEnd = cornerrad,
                        bottomStart = cornerAnim.value,
                        bottomEnd = cornerAnim.value,
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
                    .background(
                        color = secondbackgroundcolor,
                        shape = RoundedCornerShape(
                            bottomStart = cornerrad,
                            bottomEnd = cornerrad
                        )
                    )
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
