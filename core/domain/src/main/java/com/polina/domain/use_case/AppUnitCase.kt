package com.polina.domain.use_case

import android.content.SharedPreferences
import com.polina.domain.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * Инициализация аккаунта
 */
class AppInitUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend fun ensureAccountInitializedWithRetries(): Result<Int> {
        return accountRepository.getAndSavePrimaryAccount()
            .map { it.id }
    }
}
