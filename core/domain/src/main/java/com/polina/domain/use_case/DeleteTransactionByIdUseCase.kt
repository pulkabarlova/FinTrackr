package com.polina.domain.use_case

import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.transaction.Transaction
import javax.inject.Inject

class DeleteTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend fun deleteTransaction(id: Int): Result<Boolean> {
        return transactionRepository.deleteTransaction(id)
    }
}