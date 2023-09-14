package post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.User

@Composable
fun PostDisplay(
    modifier: Modifier,
    posttext: String,
    postuser: String,
    postdate: String,
    topStart: Dp,
    bottomStart: Dp,
    topEnd: Dp,
    bottomEnd: Dp,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .background(Color(242, 242, 242), RoundedCornerShape(
                topStart = topStart,
                topEnd = topEnd,
                bottomStart = bottomStart,
                bottomEnd = bottomEnd
            ))
    ) {
        Row(
            modifier = Modifier.offset(x = 8.dp, y = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(Color(255, 204, 204), RoundedCornerShape(30.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "",
                    modifier = Modifier.scale(1.3f),
                )
            }
            Text(
                text = "@$postuser",
                modifier = Modifier.offset(y = 4.dp, x = 6.dp),
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        }
        Text(
            text = "Posted: $postdate",
            modifier = Modifier.offset(y = -(15).dp, x = 55.dp),
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 8.sp
        )
        Text(
            text = posttext,
            modifier = Modifier.padding(start = 50.dp, end = 10.dp, bottom = 20.dp)
        )
    }
}