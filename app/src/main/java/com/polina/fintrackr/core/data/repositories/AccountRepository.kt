package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.network.AccountApiService
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val api: AccountApiService
) {

    suspend fun getAccounts() = api.getAccounts()

    suspend fun getAccountById(id: Int) = api.getAccountById(id)

    suspend fun createAccount(account: AccountCreateRequest) = api.createAccount(account)

    suspend fun updateAccount(id: Int, account: Account) = api.updateAccount(id, account)

    suspend fun deleteAccount(id: Int) = api.deleteAccount(id)
}