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

    private val noInternet = "Оффлайн режим, нет интернета"
    private val errorData = "Ошибка загрузки данных"

    init {
        viewModelScope.launch {
            val connected = networkMonitor.isConnected()
            _isConnected.value = connected

            if (!connected) {
                _errorMessage.value = noInternet
            }

            val result = appInitUseCase.ensureAccountInitializedWithRetries()
            _accountInitialized.value = result.isSuccess
            if (connected) {
                try {
                    appInitUseCase.loadAllTransactions()
                    appInitUseCase.loadAllCategories()
                }
                catch (e: Exception){
                    _errorMessage.value = errorData
                }
            }
        }
    }
}

