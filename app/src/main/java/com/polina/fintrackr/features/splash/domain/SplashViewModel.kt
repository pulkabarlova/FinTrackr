package com.polina.fintrackr.features.splash.domain


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.use_case.AppInitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appInitUseCase: AppInitUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _accountInitialized = mutableStateOf<Boolean?>(null)
    val accountInitialized: State<Boolean?> = _accountInitialized

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isConnected = mutableStateOf(true)
    val isConnected: State<Boolean> = _isConnected

    init {
        monitorNetwork()
        initializeAccount()
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { isConnectedNow ->
                _isConnected.value = isConnectedNow
                if (!isConnectedNow) {
                    _errorMessage.value = "Нет подключения к интернету"
                } else if (_accountInitialized.value == false && _errorMessage.value != null) {
                    _errorMessage.value = null
                    initializeAccount()
                }
            }
        }
    }

    private fun initializeAccount() {
        viewModelScope.launch {
            val result = appInitUseCase.ensureAccountInitializedWithRetries()
            result.onSuccess {
                _accountInitialized.value = true
            }.onFailure {
                _errorMessage.value = it.message
                _accountInitialized.value = false
            }
        }
    }
}
