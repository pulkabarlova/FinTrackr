package com.polina.fintrackr.core.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListItemUi(
    item: ListItem,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val rowModifier = modifier
        .fillMaxWidth()
        .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer)
        .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
        .padding(horizontal = 16.dp, vertical = 12.dp)

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // LEADING ICON
        item.leadingIcon?.let { icon ->
            val iconModifier = Modifier
            when (icon) {
                is String -> {
                    Box(
                        modifier = Modifier
                            //.wrapContentSize()//на случай если овал можно
                            .size(24.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            )
                            ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = icon,
                            //style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            fontSize = 10.sp,//оставлю как в фигме
                            //modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = iconModifier,
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        // CONTENT TEXT+SUBTEXT
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            item.subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        // TRAILING TEXT+ICON
        item.trailingText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        item.trailingIcon?.let { icon ->
            when (icon) {
                is Int -> Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }

}