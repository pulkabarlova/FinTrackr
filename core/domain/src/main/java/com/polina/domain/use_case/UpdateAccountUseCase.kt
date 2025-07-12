package com.polina.domain.use_case

import android.util.Log
import com.polina.model.dto.request.AccountCreateRequest
import com.polina.model.dto.response.AccountResponse
import com.polina.domain.repositories.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend fun updateAccount(id: Int, account: AccountCreateRequest): Result<AccountResponse> {
        return accountRepository.updateAccount(id, account)
    }
}