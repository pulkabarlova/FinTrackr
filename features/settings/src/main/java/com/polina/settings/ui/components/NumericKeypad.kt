package com.polina.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon

@Composable
fun NumericKeypad(
    onDigitClick: (String) -> Unit,
    onDelete: () -> Unit
) {
    val buttons = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "<")
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (label in row) {
                    when (label) {
                        "" -> Spacer(modifier = Modifier.size(64.dp))
                        "<" -> IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(64.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Удалить")
                        }
                        else -> Button(
                            onClick = { onDigitClick(label) },
                            modifier = Modifier.size(64.dp),
                            shape = CircleShape
                        ) {
                            Text(label, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
