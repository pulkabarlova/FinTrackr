package com.polina.fintrackr.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModelProvider
import com.polina.fintrackr.BuildConfig
import com.polina.fintrackr.app.di.App
import com.polina.fintrackr.app.di.LocalActivityComponent
import com.polina.settings.ui.SettingsViewModel
import com.polina.ui.navigation.LocalFactoryProvider
import com.polina.ui.navigation.ViewModelFactoryProvider
import com.polina.ui.theme.FinTrackrTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    private val activityComponent by lazy {
        (application as App).appComponent
            .activityComponent()
            .create()
    }
    private val settingsViewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, activityComponent.viewModelProvider())[SettingsViewModel::class.java]
    }

    private fun updateLocale(language: String) {
        val locale = Locale(language)
        val appLocale = LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        AppCompatDelegate.setApplicationLocales(appLocale)
        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(
                LocalActivity provides this,
                LocalActivityComponent provides activityComponent,
                LocalFactoryProvider provides object : ViewModelFactoryProvider {
                    override fun viewModelFactory(): ViewModelProvider.Factory {
                        return activityComponent.viewModelProvider()
                    }
                }
            ) {
                val theme by settingsViewModel.darkTheme.collectAsState()
                val colorTheme by settingsViewModel.colorTheme.collectAsState()
                val currentLanguage by settingsViewModel.language.collectAsState()
                settingsViewModel.version = BuildConfig.VERSION_NAME
                LaunchedEffect(currentLanguage) {
                    updateLocale(currentLanguage)
                }
                FinTrackrTheme(darkTheme = (theme == "dark"), color = colorTheme) {
                    AppUi(settingsViewModel)
                }
            }
        }
    }
}
