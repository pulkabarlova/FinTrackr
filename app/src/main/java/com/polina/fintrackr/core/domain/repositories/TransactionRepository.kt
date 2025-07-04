package com.polina.fintrackr.core.domain.repositories

import com.polina.fintrackr.core.data.dto.transaction.Transaction
import com.polina.fintrackr.core.data.dto.request.TransactionRequest
import com.polina.fintrackr.core.data.dto.response.TransactionResponse
import retrofit2.Response
/**
 * Репозиторий для получения транзакций
 */
interface TransactionRepository {
    suspend fun getTransacionsForPeriod(
        accountId: Int,
        from: String? = null,
        to: String? = null
    ): List<TransactionResponse>

    suspend fun getTransactionById(id: Int): Response<Transaction>
    suspend fun postTransactions(transaction: TransactionRequest): Response<TransactionResponse>
    suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionRequest
    ): Response<Transaction>

    suspend fun deleteTransaction(id: Int): Response<Boolean>
}