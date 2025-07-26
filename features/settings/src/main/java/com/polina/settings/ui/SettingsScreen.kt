package com.polina.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.data.db.CryptoPref
import com.polina.settings.R
import com.polina.settings.ui.components.ColorBottomSheet
import com.polina.settings.ui.components.LanguageBottomSheet
import com.polina.settings.ui.components.SetPinScreen
import com.polina.settings.ui.components.SettingSwitchItem
import com.polina.settings.ui.components.SoundSwitchItem
import com.polina.settings.ui.components.SyncSliderBottomSheet
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi

/**
 * Отвечает за отображение UI и обработку взаимодействия пользователя.
 */
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel,
    sound: String = "true"
) {
    AppScaffold(
        topBar = { AppTopBar(R.string.settings) },
        navController = navController,
        content = { paddingValues -> Content(paddingValues = paddingValues, viewModel, sound) })
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    viewModel: SettingsViewModel,
    sound: String = "true"
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var showLanguageBottomSheet by remember { mutableStateOf(false) }
    var showPasswordField by remember { mutableStateOf(false) }
    var showSyncSlider by remember { mutableStateOf(false) }
    val setItems = listOf(
        R.string.theme, R.string.colour, R.string.sounds,
        R.string.password, R.string.synchronization,
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
                viewModel
            )
        }

        for (i in 1..5) {
            if (i == 2) {
                item {
                    SoundSwitchItem(
                        title = stringResource(setItems[2]),
                        checked = true,
                        viewModel
                    )
                }
            } else {
                item {
                    ListItemUi(
                        item = ListItem(
                            title = stringResource(setItems[i]),
                            trailingIcon = R.drawable.arrow_right
                        ),
                        onClick = {
                            if (i == 4) {
                                showSyncSlider = true
                            }
                            if (i == 1) {
                                showBottomSheet = true
                            } else if (i == 5) {
                                showLanguageBottomSheet = true
                            } else if (i == 3) {
                                showPasswordField = true
                            }
                        }
                    )
                }
            }
        }
        item {
            Text(
                text = stringResource(R.string.version) + " " + viewModel.version,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp)
            )
        }
    }
    if (showBottomSheet) {
        ColorBottomSheet({ viewModel.setColor(it) }, { showBottomSheet = false })
    }
    if (showLanguageBottomSheet) {
        LanguageBottomSheet({
            viewModel.setLanguage(it)
        }, { showLanguageBottomSheet = false })
    }
    if (showPasswordField) {
        SetPinScreen(
            { showPasswordField = false },
            CryptoPref(LocalContext.current),
            "settings",
            sound
        )
    }
    if (showSyncSlider) {
        SyncSliderBottomSheet(
            current = viewModel.syncInterval.collectAsState().value,
            onValueChange = { viewModel.setSyncInterval(it) },
            onDismiss = { showSyncSlider = false }
        )
    }
}
