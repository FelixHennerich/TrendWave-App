package utilities.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * SideSheet that will be animated from below
 *
 * @param visible -> Change the visibility
 * @param modifier -> Edit the Sheet
 * @param backgroundcolor -> Color of the Sheet
 * @param content -> What will displayed on the Sheet
 */
@Composable
fun SideSheet(
    visible: Boolean,
    modifier: Modifier,
    backgroundcolor: Color,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally (
            animationSpec = tween(durationMillis = 300),
            initialOffsetX = { it }
        ),
        exit = slideOutHorizontally (
            animationSpec = tween(durationMillis = 300),
            targetOffsetX = { it }
        ),
    ){
        Column (
            modifier = modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
                .background(backgroundcolor)
                .verticalScroll(rememberScrollState())
        ){
            content()
        }
    }
}