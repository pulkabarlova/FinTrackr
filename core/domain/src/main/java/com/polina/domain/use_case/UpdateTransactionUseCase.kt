package com.polina.domain.use_case

import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import com.polina.model.dto.transaction.Transaction
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionRequest
    ): Result<TransactionResponse> {
        return transactionRepository.updateTransactionById(id, transaction)
    }
}