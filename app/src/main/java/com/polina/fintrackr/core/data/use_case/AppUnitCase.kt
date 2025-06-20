package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.repositories.AccountRepository
import javax.inject.Inject

class AppInitUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sharedPreferences: SharedPreferences,
    private val networkMonitor: NetworkMonitor
) {
    suspend fun ensureAccountInitialized(): Result<Int> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }

        if (!sharedPreferences.contains("accountId")) {
            val response = accountRepository.getAccounts()
            if (response.isSuccessful) {
                response.body()?.firstOrNull()?.let { account ->
                    sharedPreferences.edit().putInt("accountId", account.id).apply()
                    return Result.success(account.id)
                }
            }
            return Result.failure(Exception("Ошибка при загрузке аккаунта"))
        }

        val accountId = sharedPreferences.getInt("accountId", -1)
        return if (accountId != -1) Result.success(accountId) else Result.failure(Exception("accountId не найден"))
    }
}
