package com.polina.domain.repositories


import com.polina.model.dto.account.Account
import com.polina.model.dto.request.AccountCreateRequest
import com.polina.model.dto.response.AccountResponse
import com.polina.ui.models.AccountModel

import retrofit2.Response
/**
 * Репозиторий для получения/создания аккаунта
 */
interface AccountRepository {
    suspend fun getAccounts(): Response<List<Account>>
    suspend fun getAccountById(id: Int): Response<AccountResponse>
    suspend fun createAccount(account: AccountCreateRequest): Response<AccountResponse>
    suspend fun updateAccount(id: Int, account: AccountCreateRequest): Result<AccountResponse>
    suspend fun deleteAccount(id: Int): Response<Boolean>
    suspend fun getAndSavePrimaryAccount(): Result<AccountModel>
}