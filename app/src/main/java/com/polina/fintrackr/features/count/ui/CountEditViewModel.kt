package com.polina.fintrackr.features.count.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.request.AccountCreateRequest
import com.polina.fintrackr.core.data.network.monitor.NetworkMonitor
import com.polina.fintrackr.core.domain.use_case.GetAndSaveAccountUseCase
import com.polina.fintrackr.core.domain.use_case.UpdateAccountUseCase
import com.polina.fintrackr.features.count.domain.AccountModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountEditViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getAndSaveAccountUseCase: GetAndSaveAccountUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {
    private val _account = MutableStateFlow(AccountModel())
    val account: StateFlow<AccountModel> = _account.asStateFlow()

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _balance = MutableStateFlow<String?>(null)
    var balance: StateFlow<String?> = _balance.asStateFlow()

    private val _currency = MutableStateFlow<String?>(null)
    var currency: StateFlow<String?> = _currency.asStateFlow()

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

    fun updateAccount() {
        var accountReq = AccountCreateRequest(
            name = account.value.name,
            balance = if (balance.value.isNullOrBlank()) account.value.balance else balance.value!!,
            currency = if (currency.value.isNullOrBlank()) account.value.currency else currency.value!!
        )
        viewModelScope.launch {
            try {
                updateAccountUseCase.updateAccount(account.value.id, accountReq)
            } catch (e: Exception) {
                _error.value = "Нет подключения к интернету"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun setBalance(value: String) {
        _balance.value = value
    }

    fun setCurrency(value: String) {
        _currency.value = value
    }
}