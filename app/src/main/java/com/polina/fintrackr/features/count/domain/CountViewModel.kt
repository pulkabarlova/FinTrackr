package com.polina.fintrackr.features.count.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.repositories.AccountRepository
import com.polina.fintrackr.core.data.use_case.GetAndSaveAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CountViewModel @Inject constructor(
    private val getAndSaveAccountUseCase: GetAndSaveAccountUseCase
) : ViewModel() {
    private val _account = mutableStateOf<AccountModel>(AccountModel())
    var account: State<AccountModel> = _account

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun getAccount() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    getAndSaveAccountUseCase()
                }
                _account.value = result
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
        getAccount()
    }

    fun clearError() {
        _error.value = null
    }
}
