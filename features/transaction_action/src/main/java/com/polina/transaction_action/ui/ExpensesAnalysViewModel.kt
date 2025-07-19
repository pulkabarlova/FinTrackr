package com.polina.transaction_action.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.use_case.CategorySummary
import com.polina.domain.use_case.GetExpensesStat
import com.polina.model.AccountNotFoundException
import com.polina.model.NetworkException
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class ExpensesAnalysViewModel @Inject constructor(
    private val useCase: GetExpensesStat,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _categoriesSum = mutableStateOf<List<CategorySummary>>(emptyList())
    val categoriesSum: State<List<CategorySummary>> = _categoriesSum

    private val noInternet = "Нет подключения к интернету"
    private val serverError = "Ошибка загрузки данных"
    private val noData = "Нет данных за период"

    private val _totalSum = mutableStateOf(0.0)
    val totalSum: State<Double> = _totalSum

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
                val data = useCase.getCategorizedTransactions(_startDate.value, _endDate.value)
                _categoriesSum.value = data.categories
                _totalSum.value = data.totalAmount

                if (data.categories.isEmpty()) {
                    _error.value = noData
                }
            } catch (e: Exception) {
                _categoriesSum.value = emptyList()
                _totalSum.value = 0.0
                _error.value = serverError
            }
        }
    }

    fun getTransactions() {
        viewModelScope.launch {
            _error.value = null
            try {
                val data = useCase.getCategorizedTransactions(null, null)
                _categoriesSum.value = data.categories
                _totalSum.value = data.totalAmount

                if (data.categories.isEmpty()) {
                    _error.value = noData
                }
            } catch (e: AccountNotFoundException) {
                _categoriesSum.value = emptyList()
                _totalSum.value = 0.0
                _error.value = noInternet
            } catch (e: NetworkException) {
                _categoriesSum.value = emptyList()
                _totalSum.value = 0.0
                _error.value = serverError
            } catch (e: Exception) {
                _categoriesSum.value = emptyList()
                _totalSum.value = 0.0
                _error.value = noInternet
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
                    if (_error.value != null || _categoriesSum.value.isEmpty()) {
                        getTransactions()
                    }
                } else {
                    _error.value = noInternet
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