package com.polina.fintrackr.features.splash.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.use_case.AppInitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appInitUseCase: AppInitUseCase
) : ViewModel() {

    private val _accountInitialized = mutableStateOf<Boolean?>(null)
    val accountInitialized: State<Boolean?> = _accountInitialized

    init {
        viewModelScope.launch {
            val accountId = appInitUseCase.ensureAccountInitialized()
            _accountInitialized.value = accountId != null
        }
    }
}