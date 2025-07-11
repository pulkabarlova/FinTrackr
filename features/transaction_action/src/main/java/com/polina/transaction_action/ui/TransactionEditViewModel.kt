package com.polina.transaction_action.ui

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.use_case.DeleteTransactionByIdUseCase
import com.polina.domain.use_case.GetCategoriesUseCase
import com.polina.domain.use_case.GetTransactionByIdUseCase
import com.polina.domain.use_case.PostTransactionUseCase
import com.polina.domain.use_case.UpdateTransactionUseCase
import com.polina.model.NetworkException
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import com.polina.model.dto.transaction.Transaction
import com.polina.ui.models.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class TransactionEditViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val networkMonitor: NetworkMonitor,
    private val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionByIdUseCase: DeleteTransactionByIdUseCase
) : ViewModel() {

    val transactionId =
        savedStateHandle.get<Int>("transactionId") ?: error("transactionId is required")

    private val noInternet = "Нет подключения к интернету"
    private val serverError = "Ошибка при обновлении транзакции"

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _accountId = MutableStateFlow(13)
    val accountId: StateFlow<Int> = _accountId.asStateFlow()

    private val _categoryId = MutableStateFlow(1)
    val categoryId: StateFlow<Int> = _categoryId.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    private val _transaction = MutableStateFlow<TransactionResponse?>(null)
    val transaction: StateFlow<TransactionResponse?> = _transaction.asStateFlow()

    private val _amount = MutableStateFlow<Double?>(null)
    val amount: StateFlow<Double?> = _amount.asStateFlow()

    private val _comment = MutableStateFlow<String?>(null)
    val comment: StateFlow<String?> = _comment.asStateFlow()

    private val _selectedDate = MutableStateFlow<Long?>(System.currentTimeMillis())
    val selectedDate: StateFlow<Long?> = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow<Pair<Int, Int>?>(null)
    val selectedTime: StateFlow<Pair<Int, Int>?> = _selectedTime.asStateFlow()

    init {
        monitorNetwork()
        fetchAccount()
        getCategories()
        fetchTransaction(transactionId)
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { connected ->
                _isConnected.value = connected
                _error.value = if (!connected) noInternet else null
            }
        }
    }

    private fun fetchAccount() {
        _accountId.value = sharedPreferences.getInt("accountId", 13)
    }

    private fun fetchTransaction(id: Int) {
        viewModelScope.launch {
            val result = getTransactionByIdUseCase.getTransactionById(id)
            result.onSuccess { transaction ->
                _transaction.value = transaction
                _error.value = null

                _amount.value = transaction.amount.toDouble()
                _comment.value = transaction.comment
                _categoryId.value = transaction.category.id

                transaction.createdAt.let { createdAtString ->
                    try {
                        val formatter = SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                            Locale.getDefault()
                        ).apply {
                            timeZone = TimeZone.getTimeZone("UTC")
                        }
                        val date = formatter.parse(createdAtString)

                        date?.let {
                            val calendar = Calendar.getInstance().apply {
                                time = it
                            }
                            _selectedDate.value = calendar.timeInMillis
                            _selectedTime.value = calendar.get(Calendar.HOUR_OF_DAY) to
                                    calendar.get(Calendar.MINUTE)
                        }
                    } catch (e: Exception) {
                        _error.value = serverError
                    }
                }

            }.onFailure {
                _error.value = noInternet
            }
        }
    }


    fun getCategories() {
        viewModelScope.launch {
            try {
                _error.value = null
                _categories.value = getCategoriesUseCase()
            } catch (e: NetworkException) {
                _error.value = noInternet
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            try {
                _error.value = null
                deleteTransactionByIdUseCase.deleteTransaction(transactionId)
            } catch (e: Exception) {
                _error.value = noInternet
            }
        }
    }

    fun updateTransactionById(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val dateMillis = _selectedDate.value ?: System.currentTimeMillis()
                val (hour, minute) = _selectedTime.value ?: (0 to 0)

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = dateMillis
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val iso8601Formatter =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }

                val transactionDate = iso8601Formatter.format(calendar.time)

                val request = TransactionRequest(
                    accountId = _accountId.value,
                    categoryId = _categoryId.value,
                    amount = _amount.value?.toString() ?: "0.0",
                    comment = _comment.value,
                    transactionDate = transactionDate
                )
                Log.d("TransactionEditViewModel", request.toString())
                updateTransactionUseCase.updateTransactionById(transactionId, request)
                _error.value = null
                onSuccess()
            } catch (e: NetworkException) {
                _error.value = noInternet
                onError(noInternet)
            } catch (e: Exception) {
                _error.value = serverError
                onError(serverError)
            }
        }
    }


    fun setDate(millis: Long) {
        _selectedDate.value = millis
    }

    fun setTime(hour: Int, minute: Int) {
        _selectedTime.value = hour to minute
    }

    fun setAmount(value: String) {
        _amount.value = value.toDoubleOrNull()
    }

    fun setComment(value: String) {
        _comment.value = value
    }

    fun setCategoryId(id: Int) {
        _categoryId.value = id
    }
}
