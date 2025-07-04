package com.polina.fintrackr.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

/**
 * Настройка топбара
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    name: Int,
    isTrailing: Any? = null,
    onTrailingIconClick: () -> Unit = {},
    isLeading: Any? = null,
    onBackIconClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )

                isTrailing?.let {
                    when (it) {
                        is ImageVector -> {
                            Icon(
                                imageVector = it,
                                contentDescription = "trailing_icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable { onTrailingIconClick() }
                            )
                        }
                        is Int -> {
                            Icon(
                                painter = painterResource(it),
                                contentDescription = "trailing_icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable { onTrailingIconClick() }
                            )
                        }
                    }
                }


                isLeading?.let {
                    when (it) {
                        is ImageVector -> {
                            Icon(
                                imageVector = it,
                                contentDescription = "leading_icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .clickable { onBackIconClick() }
                            )
                        }
                        is Int -> {
                            Icon(
                                painter = painterResource(it),
                                contentDescription = "leading_icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .clickable { onBackIconClick() }
                            )
                        }
                    }
                }
            }
        }
    )
}
