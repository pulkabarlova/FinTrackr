package com.polina.transaction_action.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.use_case.GetCategoriesUseCase
import com.polina.domain.use_case.PostTransactionUseCase
import com.polina.model.NetworkException
import com.polina.model.dto.request.TransactionRequest
import com.polina.ui.models.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionAddViewModel @Inject constructor(
    private val postTransactionUseCase: PostTransactionUseCase,
    private val networkMonitor: NetworkMonitor,
    private val sharedPreferences: SharedPreferences,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

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
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            networkMonitor.networkStatus.collect { connected ->
                _isConnected.value = connected
                _error.value = if (!connected) "Нет подключения к интернету" else null
            }
        }
    }

    private fun fetchAccount() {
        _accountId.value = sharedPreferences.getInt("accountId", 13)
    }

    fun getCategories() {
        viewModelScope.launch {
            try {
                _error.value = null
                _categories.value = getCategoriesUseCase()
            } catch (e: NetworkException) {
                _error.value = "Нет подключения к интернету"
            }
        }
    }

    fun setDate(millis: Long) {
        _selectedDate.value = millis
    }

    fun setTime(hour: Int, minute: Int) {
        _selectedTime.value = hour to minute
    }

    fun postTransaction(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val dateMillis = selectedDate.value
                val time = selectedTime.value
                val amountValue = amount.value
                val commentValue = comment.value
                val category = categoryId.value
                val account = accountId.value

                if (dateMillis == null || time == null || category == null || amountValue == null) {
                    onError("Пожалуйста, заполните все обязательные поля")
                    return@launch
                }

                val calendar = java.util.Calendar.getInstance().apply {
                    timeInMillis = dateMillis
                    set(java.util.Calendar.HOUR_OF_DAY, time.first)
                    set(java.util.Calendar.MINUTE, time.second)
                    set(java.util.Calendar.SECOND, 0)
                    set(java.util.Calendar.MILLISECOND, 0)
                }

                val isoDateTime = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault()).apply {
                    timeZone = java.util.TimeZone.getTimeZone("UTC")
                }.format(calendar.time)

                val request = TransactionRequest(
                    accountId = account,
                    categoryId = category,
                    amount = amountValue.toString(),
                    transactionDate = isoDateTime,
                    comment = commentValue
                )
                postTransactionUseCase.postTransaction(request)
                onSuccess()
            } catch (e: Exception) {
                onError("")
            }
        }
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
