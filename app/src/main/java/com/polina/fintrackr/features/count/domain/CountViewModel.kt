package com.polina.fintrackr.features.count.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    val _accounts = mutableStateOf<List<Account>>(emptyList())
    var accounts: State<List<Account>> = _accounts
    suspend fun getAccounts() {
        _accounts.value = accountRepository.getAccounts()
    }

    init {
        viewModelScope.launch {
            getAccounts()
            Log.d("hhhh", "DFDF${accounts}")
        }
    }
}