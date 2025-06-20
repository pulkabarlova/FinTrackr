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

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        viewModelScope.launch {
            val result = appInitUseCase.ensureAccountInitialized()
            result.onSuccess {
                _accountInitialized.value = true
            }.onFailure {
                _errorMessage.value = it.message
                _accountInitialized.value = false
            }
        }
    }
}