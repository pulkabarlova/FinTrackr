package com.polina.domain.use_case


import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import javax.inject.Inject

class PostTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend fun postTransaction(transaction: TransactionRequest): Result<TransactionResponse> {
        return transactionRepository.postTransactions(transaction)
    }
}