package com.polina.domain.use_case

import com.polina.domain.repositories.AccountRepository
import com.polina.ui.models.AccountModel
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