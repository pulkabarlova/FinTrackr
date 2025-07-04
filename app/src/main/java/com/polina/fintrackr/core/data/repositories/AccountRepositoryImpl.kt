package com.polina.fintrackr.core.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.polina.fintrackr.core.data.dto.request.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.account.Account
import com.polina.fintrackr.core.data.dto.response.AccountResponse
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.network.api_service.AccountApiService
import com.polina.fintrackr.core.data.network.monitor.NetworkMonitor
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import com.polina.fintrackr.features.count.domain.AccountModel
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
) : AccountRepository {

    override suspend fun getAccounts() = api.getAccounts()

    override suspend fun getAccountById(id: Int) = api.getAccountById(id)

    override suspend fun createAccount(account: AccountCreateRequest) = api.createAccount(account)

    override suspend fun updateAccount(id: Int, account: AccountCreateRequest): Result<AccountResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }
        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.updateAccount(id, account) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception("Нет подключения к интернету"))
            }
        }
        return Result.failure(Exception("Нет подключения к интернету"))
    }

    override suspend fun deleteAccount(id: Int) = api.deleteAccount(id)

    override suspend fun getAndSavePrimaryAccount(): Result<AccountModel> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }
        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.getAccounts() }
            if (response.isSuccessful) {
                val account = response.body()?.firstOrNull()
                return if (account != null) {
                    sharedPreferences.edit()
                        .putInt("accountId", account.id)
                        .apply()
                    Result.success(account.toAccountModel())
                } else {
                    Result.failure(Exception("Аккаунт не найден"))
                }
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception("Нет подключения к интернету"))
            }
        }
        return Result.failure(Exception("Нет подключения к интернету"))
    }
}