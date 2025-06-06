package com.yousufjamil.myjconnect_to_web.accessories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    icon: ImageVector? = null,
    title: String,
    trailingText: String? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .padding(10.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (icon != null) {
                Image(
                    imageVector = icon,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f)
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (trailingText != null) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = trailingText,
                    color = Color.Gray
                )
            }

            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onTrailingIconClick?.invoke()
                        }
                )
            }
        }
    }
}

data class ListItemDataType(
    val icon: ImageVector? = null,
    val title: String,
    val trailingIcon: ImageVector? = null,
    val onClick: () -> Unit
)