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
) : ViewModel() {

    private val _transactions = mutableStateOf<List<TransactionResponse>>(emptyList())
    val transactions: State<List<TransactionResponse>> = _transactions

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun getTransactions() {
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    useCase.getTransactionsForPeriod()
                }
                _transactions.value = list
                _error.value = null
            } catch (e: AccountNotFoundException) {
                _error.value = "Аккаунт не найден"
            } catch (e: NetworkException) {
                _error.value = "Ошибка при выходе в сеть, проверьте соединение"
            } catch (e: Exception) {
                _error.value = "Ошибка при выходе в сеть, проверьте соединение"
            }
        }
    }

    init {
        getTransactions()
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