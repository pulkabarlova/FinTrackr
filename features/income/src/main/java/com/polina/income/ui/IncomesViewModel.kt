package com.polina.income.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.model.AccountNotFoundException
import com.polina.model.NetworkException
import com.polina.ui.models.IncomeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
/**
 * Управляет состоянием и логикой отображения экрана доходов пользователя.
 */
@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val useCase: com.polina.domain.use_case.TransactionUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _incomes = mutableStateOf<List<IncomeModel>>(emptyList())
    val incomes: State<List<IncomeModel>> = _incomes

    private val _totalIncomes = mutableStateOf(0.0)
    val totalIncomes: State<Double> = _totalIncomes

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _isConnected = mutableStateOf(true)
    val isConnected: State<Boolean> = _isConnected

    private val _startDate = mutableStateOf<Date?>(null)
    val startDate: State<Date?> = _startDate

    private val _endDate = mutableStateOf<Date?>(null)
    val endDate: State<Date?> = _endDate

    fun setStartDate(date: Date) {
        _startDate.value = date
        reloadTransactions()
    }

    fun setEndDate(date: Date) {
        _endDate.value = date
        reloadTransactions()
    }

    fun reloadTransactions() {
        viewModelScope.launch {
            _error.value = null
            try {
                val data = useCase.getTransactionsDataWithRetries(_startDate.value, _endDate.value)
                _incomes.value = data.incomes
                _totalIncomes.value = data.totalIncomes
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки данных"
            }
        }
    }

    fun getTransactions() {
        viewModelScope.launch {
            _error.value = null
            try {
                val data = useCase.getTransactionsDataWithRetries(_startDate.value, _endDate.value)
                _incomes.value = data.incomes
                _totalIncomes.value = data.totalIncomes
                _error.value = null
            } catch (e: AccountNotFoundException) {
                _error.value = "Нет подключения к интернету"
            } catch (e: NetworkException) {
                _error.value = "Нет подключения к интернету"
            } catch (e: Exception) {
                _error.value = "Нет подключения к интернету"
            }
        }
    }

    init {
        getTransactions()
        monitorNetwork()
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { connected ->
                _isConnected.value = connected
                if (connected) {
                    if (_error.value != null || _incomes.value.isEmpty()) {
                        getTransactions()
                    }
                } else {
                    _error.value = "Нет подключения к интернету"
                }
            }
        }
    }

    fun formatDateTime(isoString: String?): Pair<String, String> {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val date: Date = inputFormat.parse(isoString ?: return "" to "") ?: return "" to ""

            val dateFormatter = SimpleDateFormat("dd MMMM", Locale("ru"))
            val timeFormatter = SimpleDateFormat("HH:mm", Locale("ru"))

            val formattedDate = dateFormatter.format(date)
            val formattedTime = timeFormatter.format(date)

            formattedDate to formattedTime
        } catch (e: Exception) {
            return "" to ""
        }
    }

    fun clearError() {
        _error.value = null
    }
}