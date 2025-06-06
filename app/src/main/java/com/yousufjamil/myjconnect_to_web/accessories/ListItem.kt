package com.yousufjamil.myjconnect_to_web.accessories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    icon: ImageVector? = null,
    title: String,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .padding(10.dp)
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
        if (trailingIcon != null) {
            Spacer(modifier = Modifier.width(15.dp))
            Image(
                imageVector = trailingIcon,
                contentDescription = null
            )
        }
    }
}

data class ListItemDataType(
    val icon: ImageVector? = null,
    val title: String,
    val trailingIcon: ImageVector? = null,
    val onClick: () -> Unit
)