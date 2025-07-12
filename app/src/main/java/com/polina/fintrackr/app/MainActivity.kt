package com.polina.fintrackr.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.polina.fintrackr.app.di.App
import com.polina.fintrackr.app.di.LocalActivityComponent
import com.polina.ui.navigation.LocalFactoryProvider
import com.polina.ui.navigation.ViewModelFactoryProvider
import com.polina.ui.theme.FinTrackrTheme


class MainActivity : ComponentActivity() {
    private val activityComponent by lazy {
        (application as App).appComponent
            .activityComponent()
            .create()
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
                FinTrackrTheme {
                    AppUi()
                }
            }
        }
    }
}
