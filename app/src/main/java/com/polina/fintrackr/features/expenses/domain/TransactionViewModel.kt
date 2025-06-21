package com.polina.fintrackr.features.expenses.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import com.polina.fintrackr.core.data.mapper.toExpenseModel
import com.polina.fintrackr.core.data.mapper.toIncomeModel
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.use_case.TransactionUseCase
import com.polina.fintrackr.features.incoms.domain.IncomeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val useCase: TransactionUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _transactions = mutableStateOf<List<TransactionResponse>>(emptyList())
    val transactions: State<List<TransactionResponse>> = _transactions

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
                val list = withContext(Dispatchers.IO) {
                    useCase.getTransactionsForPeriodWithRetries(
                        _startDate.value,
                        _endDate.value
                    )
                }
                _transactions.value = list
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки данных"
            }
        }
    }

    fun getTransactions() {
        viewModelScope.launch {
            _error.value = null
            try {
                val list = withContext(Dispatchers.IO) {
                    useCase.getTransactionsForPeriodWithRetries(_startDate.value, _endDate.value)
                }
                _transactions.value = list
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
                    if (_error.value != null || _transactions.value.isEmpty()) {
                        getTransactions()
                    }
                } else {
                    _error.value = "Нет подключения к интернету"
                }
            }
        }
    }

    private val groupedTransactions: Pair<Pair<List<IncomeModel>, Double>, Pair<List<ExpenseModel>, Double>>
        get() {
            val incomes = mutableListOf<IncomeModel>()
            val expenses = mutableListOf<ExpenseModel>()
            var sumIncomes = 0.0
            var sumExpenses = 0.0

            _transactions.value.forEach { tx ->
                if (tx.category.isIncome) {
                    incomes.add(tx.toIncomeModel())
                    sumIncomes += tx.amount.toFloat()
                } else {
                    expenses.add(tx.toExpenseModel())
                    sumExpenses += tx.amount.toFloat()
                }
            }
            return Pair(Pair(incomes, sumIncomes), Pair(expenses, sumExpenses))
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

    val incomes: List<IncomeModel> get() = groupedTransactions.first.first
    val expenses: List<ExpenseModel> get() = groupedTransactions.second.first
    val totalIncomes: Double get() = groupedTransactions.first.second
    val totalExpenses: Double get() = groupedTransactions.second.second
}