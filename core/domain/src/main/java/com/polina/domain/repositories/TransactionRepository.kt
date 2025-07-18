package com.polina.domain.repositories

import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import com.polina.model.dto.transaction.Transaction
import retrofit2.Response
import java.util.Date

/**
 * Репозиторий для получения транзакций
 */
interface TransactionRepository {
    suspend fun getTransacionsForPeriod(
        accountId: Int,
        from: Date? = null,
        to: Date? = null
    ): List<TransactionResponse>

    suspend fun getTransactionById(id: Int): Result<TransactionResponse>
    suspend fun postTransactions(transaction: TransactionRequest): Result<TransactionResponse>
    suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionRequest
    ): Result<TransactionResponse>

    suspend fun deleteTransaction(id: Int): Result<Boolean>
    suspend fun syncTransactions()
}