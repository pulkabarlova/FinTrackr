package com.polina.fintrackr.core.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            fontSize = 18.sp,
                        )
                    }
                }
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = iconModifier,
                    tint = Color.Unspecified,
                )
                is Int -> Icon(
                    painter = painterResource(id = icon),
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
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,)
            item.subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,)
            }
        }
        // TRAILING TEXT+ICON
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            item.trailingText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            item.trailingBottomText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item.trailingIcon?.let { icon ->
            when (icon) {
                is Int -> Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }
    }

}