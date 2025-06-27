package com.polina.fintrackr.features.count.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.network.monitor.NetworkMonitor
import com.polina.fintrackr.core.domain.use_case.GetAndSaveAccountUseCase
import com.polina.fintrackr.features.count.domain.AccountModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * Управляет состоянием и логикой отображения экрана аккаунта пользователя.
 */
@HiltViewModel
class CountViewModel @Inject constructor(
    private val getAndSaveAccountUseCase: GetAndSaveAccountUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _account = mutableStateOf(AccountModel())
    val account: State<AccountModel> = _account

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _isConnected = mutableStateOf(true)
    val isConnected: State<Boolean> = _isConnected

    init {
        monitorNetwork()
        fetchAccount()
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { connected ->
                _isConnected.value = connected
                if (!connected) {
                    _error.value = "Нет подключения к интернету"
                } else if (_error.value != null) {
                    _error.value = null
                    fetchAccount()
                }
            }
        }
    }

    private fun fetchAccount() {
        viewModelScope.launch {
            val result = getAndSaveAccountUseCase()
            result.onSuccess { accountModel ->
                _account.value = accountModel
                _error.value = null
            }.onFailure {
                _error.value = "Нет подключения к интернету"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
