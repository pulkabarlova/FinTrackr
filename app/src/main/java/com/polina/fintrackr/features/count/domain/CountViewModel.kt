package com.polina.fintrackr.features.count.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _account = mutableStateOf<AccountModel>(AccountModel())
    var account: State<AccountModel> = _account
    private suspend fun getAccounts() {
        val response: Response<List<Account>> = accountRepository.getAccounts()
        if (response.isSuccessful){
            response.body()?.firstOrNull()?.let { accountDto ->
                _account.value = accountDto.toAccountModel()
            }
        }
    }

    init {
        viewModelScope.launch {
            getAccounts()
        }
    }
}