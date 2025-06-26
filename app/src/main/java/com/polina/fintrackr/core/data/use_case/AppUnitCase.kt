package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AppInitUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sharedPreferences: SharedPreferences,
    private val networkMonitor: NetworkMonitor
) {
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
            val response = accountRepository.getAccounts()
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
