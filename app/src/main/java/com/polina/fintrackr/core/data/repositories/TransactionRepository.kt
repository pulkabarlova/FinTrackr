package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.dto.transaction.TransactionRequest
import com.polina.fintrackr.core.data.network.CategoryApiService
import com.polina.fintrackr.core.data.network.TransactionApiService
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val api: TransactionApiService
) {
    suspend fun getTransacionsForPeriod(accountId: Int, from: String, to: String) =
        api.getTransacionsForPeriod(accountId, from, to)

    suspend fun postTransactions(transaction: TransactionRequest) =
        api.postTransactions(transaction)

    suspend fun getTransactionById(id: Int) =
        api.getTransactionById(id)

    suspend fun updateTransactionById(id: Int, transaction: TransactionRequest) =
        api.updateTransactionById(id, transaction)

    suspend fun deleteTransaction(id: Int) =
        api.deleteTransaction(id)
}