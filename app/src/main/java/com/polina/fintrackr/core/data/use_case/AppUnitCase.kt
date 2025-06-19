package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.repositories.AccountRepository
import javax.inject.Inject

class AppInitUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun ensureAccountInitialized(): Int? {
        if (!sharedPreferences.contains("accountId")) {
            val response = accountRepository.getAccounts()
            if (response.isSuccessful) {
                response.body()?.firstOrNull()?.let { account ->
                    sharedPreferences.edit().putInt("accountId", account.id).apply()
                    return account.id
                }
            }
            return null
        }
        return sharedPreferences.getInt("accountId", -1).takeIf { it != -1 }
    }
}