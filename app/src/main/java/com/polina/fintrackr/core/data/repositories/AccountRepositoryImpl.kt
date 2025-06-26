package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.network.AccountApiService
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApiService
) : AccountRepository {

    override suspend fun getAccounts() = api.getAccounts()

    override suspend fun getAccountById(id: Int) = api.getAccountById(id)

    override suspend fun createAccount(account: AccountCreateRequest) = api.createAccount(account)

    override suspend fun updateAccount(id: Int, account: Account) = api.updateAccount(id, account)

    override suspend fun deleteAccount(id: Int) = api.deleteAccount(id)
}