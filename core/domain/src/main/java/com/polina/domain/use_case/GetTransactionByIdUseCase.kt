package com.polina.domain.use_case

import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.response.TransactionResponse
import com.polina.model.dto.transaction.Transaction
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend fun getTransactionById(id: Int): Result<TransactionResponse> {
        return transactionRepository.getTransactionById(id)
    }
}