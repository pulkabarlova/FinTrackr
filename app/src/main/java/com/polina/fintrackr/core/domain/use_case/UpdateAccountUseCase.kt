package com.polina.fintrackr.core.domain.use_case

import android.util.Log
import com.polina.fintrackr.core.data.dto.request.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.response.AccountResponse
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend fun updateAccount(id: Int, account: AccountCreateRequest): Result<AccountResponse> {
        return accountRepository.updateAccount(id, account)
    }
}