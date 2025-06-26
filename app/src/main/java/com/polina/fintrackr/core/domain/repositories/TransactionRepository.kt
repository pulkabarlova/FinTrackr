package com.polina.fintrackr.core.domain.repositories

import com.polina.fintrackr.core.data.dto.model.transaction.Transaction
import com.polina.fintrackr.core.data.dto.transaction.TransactionRequest
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import retrofit2.Response

interface TransactionRepository {
    suspend fun getTransacionsForPeriod(
        accountId: Int,
        from: String,
        to: String
    ): Response<List<TransactionResponse>>

    suspend fun getTransactionById(id: Int): Response<Transaction>
    suspend fun postTransactions(transaction: TransactionRequest): Response<TransactionResponse>
    suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionRequest
    ): Response<Transaction>

    suspend fun deleteTransaction(id: Int): Response<Boolean>
}