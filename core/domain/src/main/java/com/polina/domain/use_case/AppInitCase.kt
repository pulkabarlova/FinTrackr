package com.polina.domain.use_case

import com.polina.domain.repositories.AccountRepository
import com.polina.domain.repositories.TransactionRepository
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
/**
 * Инициализация аккаунта
 */
class AppInitUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoriesUseCase: GetCategoriesUseCase
) {
    suspend fun ensureAccountInitializedWithRetries(): Result<Int> {
        return accountRepository.getAndSavePrimaryAccount()
            .map { it.id }
    }
    suspend fun loadAllTransactions() {
        transactionRepository.syncTransactions()
    }
    suspend fun loadAllCategories(){
        categoriesUseCase.invoke()
    }
}
