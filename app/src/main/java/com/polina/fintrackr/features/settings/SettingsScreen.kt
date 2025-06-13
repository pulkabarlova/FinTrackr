package com.polina.fintrackr.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.AppScaffold
import com.polina.fintrackr.core.ui.ListItem
import com.polina.fintrackr.core.ui.ListItemUi

@Composable
fun SettingsScreen(navController: NavController) {
    AppScaffold(
        topBar = { TopBar() },
        navController = navController,
        content = { paddingValues -> Content(paddingValues = paddingValues) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(
                text = stringResource(id = R.string.settings),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
fun Content(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        item {
            SettingSwitchItem(
                title = "Текст",
                checked = false,
                onCheckedChange = { }
            )
        }
        repeat(7) {
            item {
                ListItemUi(
                    item = ListItem(
                        title = "Текст",
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
            .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
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