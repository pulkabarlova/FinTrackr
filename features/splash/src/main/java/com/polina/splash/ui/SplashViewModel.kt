package com.polina.splash.ui


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.use_case.AppInitUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * Управляет состоянием и логикой отображения сплэш экрана.
 */
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

    private val noInternet = "Нет подключения к интернету"

    init {
        monitorNetwork()
        initializeAccount()
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { isConnectedNow ->
                _isConnected.value = isConnectedNow
                if (!isConnectedNow) {
                    _errorMessage.value = noInternet
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
                _errorMessage.value = noInternet
                _accountInitialized.value = false
            }
        }
    }
}
