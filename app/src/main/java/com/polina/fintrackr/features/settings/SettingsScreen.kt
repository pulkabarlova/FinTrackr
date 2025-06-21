package com.polina.fintrackr.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.ui.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.components.AppScaffold
import com.polina.fintrackr.core.ui.components.AppTopBar
import com.polina.fintrackr.core.ui.components.ListItem
import com.polina.fintrackr.core.ui.components.ListItemUi

@Composable
fun SettingsScreen(navController: NavController) {
    AppScaffold(
        topBar = { AppTopBar(R.string.settings) },
        navController = navController,
        content = { paddingValues -> Content(paddingValues = paddingValues) })
}

@Composable
fun Content(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
    val setItems = listOf(
        R.string.theme, R.string.colour, R.string.sounds,
        R.string.haptiki, R.string.password, R.string.synchronization,
        R.string.language, R.string.about
    )
    LazyColumn(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        item {
            SettingSwitchItem(
                title = stringResource(setItems[0]),
                checked = false,
                onCheckedChange = { },
            )
        }

        for (i in 1..6) {
            item {
                ListItemUi(
                    item = ListItem(
                        title = stringResource(setItems[i]),
                        trailingIcon = R.drawable.arrow_right
                    ),
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun SettingSwitchItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange, Modifier.padding(end = 7.dp))
    }
}


@Preview(name = "Light Mode", showSystemUi = true)
// @Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        SettingsScreen(navController = NavController(LocalContext.current))
    }
}