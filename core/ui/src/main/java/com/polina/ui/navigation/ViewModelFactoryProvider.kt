package com.polina.ui.navigation

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryProvider {
    fun viewModelFactory(): ViewModelProvider.Factory
}