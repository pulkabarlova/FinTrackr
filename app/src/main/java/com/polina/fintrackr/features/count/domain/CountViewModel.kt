package com.polina.fintrackr.features.count.domain

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.mapper.toAccountModel
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAndSaveAccountUseCase()
            result?.let {
                _account.value = it
            }
        }
    }
}