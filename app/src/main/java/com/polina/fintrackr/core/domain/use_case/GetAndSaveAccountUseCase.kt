package com.polina.fintrackr.core.domain.use_case

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.network.monitor.NetworkMonitor
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import com.polina.fintrackr.features.count.domain.AccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * Сохранение аккаунта
 */
class GetAndSaveAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): Result<AccountModel> {
        return accountRepository.getAndSavePrimaryAccount()
    }
}