package com.polina.fintrackr.features.count.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.use_case.GetAndSaveAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
            val result = withContext(Dispatchers.IO) {
                getAndSaveAccountUseCase()
            }
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
