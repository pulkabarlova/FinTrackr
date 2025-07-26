package com.polina.count.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.use_case.GetAndSaveAccountUseCase
import com.polina.domain.use_case.GetJulyAcountAnalys
import com.polina.ui.models.AccountModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
/**
 * Управляет состоянием и логикой отображения экрана аккаунта пользователя.
 */

class CountViewModel @Inject constructor(
    private val getAndSaveAccountUseCase: GetAndSaveAccountUseCase,
    private val networkMonitor: NetworkMonitor,
    private val getAnalysUseCase: GetJulyAcountAnalys
) : ViewModel() {

    private val _account = mutableStateOf(AccountModel())
    val account: State<AccountModel> = _account

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _isConnected = mutableStateOf(true)
    val isConnected: State<Boolean> = _isConnected

    private val _monthlyIncomeMinusExpenses = MutableStateFlow<Double>(0.0)
    val monthlyIncomeMinusExpenses: StateFlow<Double> = _monthlyIncomeMinusExpenses.asStateFlow()

    private val noInternet = "Нет подключения к интернету"

    init {
        monitorNetwork()
        fetchAccount()
        getAnalys()
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { connected ->
                _isConnected.value = connected
                if (!connected) {
                    _error.value = noInternet
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
                _error.value = noInternet
            }
        }
    }

    private fun getAnalys() {
        viewModelScope.launch {
            try {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2025)
                    set(Calendar.MONTH, 6)
                    set(Calendar.DAY_OF_MONTH, 1)
                }
                val startDate = getStartOfMonth(calendar.time)
                val endDate = getEndOfMonth(calendar.time)

                val data = getAnalysUseCase.getIncomeMinusExpenses(startDate, endDate)
                _monthlyIncomeMinusExpenses.value = data
            } catch (e: Exception) {
                _error.value = noInternet
            }
        }
    }

    fun getStartOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    fun getEndOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.time
    }

    fun clearError() {
        _error.value = null
    }
}
