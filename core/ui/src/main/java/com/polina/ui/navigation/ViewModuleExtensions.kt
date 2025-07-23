package com.polina.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel


val LocalFactoryProvider = staticCompositionLocalOf<ViewModelFactoryProvider> {
    error("No ViewModelFactoryProvider provided!")
}


@Composable
inline fun <reified VM : ViewModel> daggerViewModel(): VM {
    val factory = LocalFactoryProvider.current.viewModelFactory()
    return viewModel(factory = factory)
}
