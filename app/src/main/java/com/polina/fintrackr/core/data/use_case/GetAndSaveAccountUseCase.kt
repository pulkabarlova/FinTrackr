package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.repositories.AccountRepository
import com.polina.fintrackr.features.count.domain.AccountModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetAndSaveAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sharedPreferences: SharedPreferences,
    private val networkMonitor: NetworkMonitor
) {
    suspend operator fun invoke(): Result<AccountModel> {
        return getAccountWithRetries()
    }

    private suspend fun getAccountWithRetries(): Result<AccountModel> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception())
        }

        repeat(3) { attempt ->
            val result = getAccount()
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

    private suspend fun getAccount(): Result<AccountModel> {
        val response = accountRepository.getAccounts()

        return when {
            response.isSuccessful -> {
                response.body()?.firstOrNull()?.let { account ->
                    sharedPreferences.edit()
                        .putInt("accountId", account.id)
                        .apply()
                    Result.success(account.toAccountModel())
                } ?: Result.failure(Exception())
            }
            response.code() == 500 -> {
                Result.failure(Exception())
            }
            else -> {
                Result.failure(Exception())
            }
        }
    }
}