package com.polina.fintrackr.features.history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.ui.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.components.AppScaffold
import com.polina.fintrackr.core.ui.components.AppTopBar
import com.polina.fintrackr.core.ui.components.ListItem
import com.polina.fintrackr.core.ui.components.ListItemUi

@Composable
fun HistoryScreen(navController: NavController) {
    AppScaffold(
        navController = navController,
        content = { paddingValues -> Content(paddingValues) },
        topBar = {
            AppTopBar(R.string.history, R.drawable.texthistory,
                {}, Icons.Default.KeyboardArrowLeft, {})
        })
}

@Composable
fun Content(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.start),
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.end),
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.summ),
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }

        }
    }
}


@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        HistoryScreen(navController = NavController(LocalContext.current))
    }
}