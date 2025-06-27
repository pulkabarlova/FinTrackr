package com.polina.fintrackr.core.data.repositories

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.network.api_service.AccountApiService
import com.polina.fintrackr.core.data.network.monitor.NetworkMonitor
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import com.polina.fintrackr.features.count.domain.AccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
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

    override suspend fun updateAccount(id: Int, account: Account) = api.updateAccount(id, account)

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
                return Result.failure(Exception("API error: ${response.code()}"))
            }
        }
        return Result.failure(Exception("Failed after retries"))
    }
    suspend fun ensureAccountInitializedWithRetries(): Result<Int> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception())
        }
        repeat(3) { attempt ->
            val result = ensureAccountInitialized()
            if (result.isSuccess) return result
            val exception = result.exceptionOrNull()
            if (exception?.message?.contains("500") == true && attempt < 2) {
                delay(2000)
            } else {
                return result
            }
        }
        return Result.failure(Exception())
    }

    private suspend fun ensureAccountInitialized(): Result<Int> {
        if (!sharedPreferences.contains("accountId")) {
            val response = withContext(Dispatchers.IO) { getAccounts() }
            if (response.isSuccessful) {
                response.body()?.firstOrNull()?.let { account ->
                    sharedPreferences.edit().putInt("accountId", account.id).apply()
                    return Result.success(account.id)
                }
            } else if (response.code() == 500) {
                return Result.failure(Exception())
            }
            return Result.failure(Exception())
        }

        val accountId = sharedPreferences.getInt("accountId", -1)
        return if (accountId != -1) Result.success(accountId)
        else Result.failure(Exception())
    }
}