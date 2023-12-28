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
    THEMA2("Travel and Adventure"),
    THEMA3("Photography"),
    THEMA4("Art and Creativity"),
    THEMA5("Health and Wellness"),
    THEMA6("Fashion and Style"),
    THEMA7("Technology and Innovation"),
    THEMA8("Music and Concerts"),
    THEMA9("Film and Television"),
    THEMA10("Sports and Fitness"),
    THEMA11("Food and Cooking"),
    THEMA12("Pets and Animals"),
    THEMA13("Environmental Protection and Sustainability"),
    THEMA14("Science and Research"),
    THEMA15("Books and Literature"),
    THEMA16("DIY and Crafts"),
    THEMA17("Gaming and Games"),
    THEMA18("Scientific Discoveries"),
    THEMA19("Cars and Motorcycles"),
    THEMA20("History and Culture"),
    THEMA21("Comedy and Humor"),
    THEMA22("Photo Editing and Graphic Design"),
    THEMA23("Education and Learning"),
    THEMA24("Social Justice and Activism"),
    THEMA25("Psychology and Self-Improvement"),
    THEMA26("Spiritual Development"),
    THEMA27("Business and Career"),
    THEMA28("Parenting and Family"),
    THEMA29("Outdoor Activities"),
    THEMA30("Meditation and Mindfulness"),
    THEMA31("Digital Art and Illustration"),
    THEMA32("Recipes and Cooking Tips"),
    THEMA33("Body Art and Tattoos"),
    THEMA34("Music Production and DJing"),
    THEMA35("Current News and Events"),
    THEMA36("Sports Events and Tournaments"),
    THEMA37("Gardening and Plants"),
    THEMA38("Space and Astronomy"),
    THEMA39("Motorcycling and Biker Culture"),
    THEMA40("LGBTQ+ Topics and Activism"),
    THEMA41("Hair Care and Beauty"),
    THEMA42("Professional Development and Tips"),
    THEMA43("Yoga and Meditation"),
    THEMA44("Language Learning and Culture"),
    THEMA45("Parenting and Upbringing"),
    THEMA46("Technology Trends and Gadgets"),
    THEMA47("Astrology and Horoscopes"),
    THEMA48("Virtual Reality and Augmented Reality"),
    THEMA49("Nightlife and Events"),
    THEMA50("Self-Defense and Safety"),
    THEMA51("Philosophy and Thought-provoking Ideas"),

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
