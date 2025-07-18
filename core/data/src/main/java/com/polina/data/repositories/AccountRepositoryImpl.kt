package com.polina.data.repositories

import android.content.SharedPreferences
import com.polina.data.db.AccountDao
import com.polina.data.db.mapper.toAccount
import com.polina.data.db.mapper.toAccountEntity
import com.polina.model.dto.request.AccountCreateRequest
import com.polina.model.dto.response.AccountResponse
import com.polina.model.mapper.toAccountModel
import com.polina.data.network.api_service.AccountApiService
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.repositories.AccountRepository
import com.polina.ui.models.AccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Репозиторий для получения/создания аккаунта
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApiService,
    private val networkMonitor: NetworkMonitor,
    private val sharedPreferences: SharedPreferences,
    private val accountDao: AccountDao
) : AccountRepository {

    override suspend fun getAccounts() = api.getAccounts()

    override suspend fun getAccountById(id: Int) = api.getAccountById(id)

    override suspend fun createAccount(account: AccountCreateRequest) = api.createAccount(account)

    override suspend fun updateAccount(id: Int, account: AccountCreateRequest): Result<AccountResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception())
        }
        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.updateAccount(id, account) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception())
            }
        }
        return Result.failure(Exception())
    }

    override suspend fun deleteAccount(id: Int) = api.deleteAccount(id)

    override suspend fun getAndSavePrimaryAccount(): Result<AccountModel> {
        if (!networkMonitor.isConnected()) {
            val localAccount = accountDao.getAccount()
            return if (localAccount != null) {
                Result.success(localAccount.toAccount().toAccountModel())
            } else {
                Result.failure(Exception())
            }
        }
        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.getAccounts() }
            if (response.isSuccessful) {
                val account = response.body()?.firstOrNull()
                return if (account != null) {
                    sharedPreferences.edit()
                        .putInt("accountId", account.id)
                        .apply()
                    accountDao.insertAccount(account.toAccountEntity())
                    Result.success(account.toAccountModel())
                } else {
                    Result.failure(Exception())
                }
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception())
            }
        }
        return Result.failure(Exception())
    }
}