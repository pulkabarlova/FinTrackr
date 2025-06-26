package com.polina.fintrackr.core.domain.repositories

import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.account.AccountResponse
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.network.AccountApiService
import retrofit2.Response
import javax.inject.Inject

interface AccountRepository {
    suspend fun getAccounts(): Response<List<Account>>
    suspend fun getAccountById(id: Int): Response<AccountResponse>
    suspend fun createAccount(account: AccountCreateRequest): Response<AccountResponse>
    suspend fun updateAccount(id: Int, account: Account): Response<AccountResponse>
    suspend fun deleteAccount(id: Int): Response<Boolean>
}