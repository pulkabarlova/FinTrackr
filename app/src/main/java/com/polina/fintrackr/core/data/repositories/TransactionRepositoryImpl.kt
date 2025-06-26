package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.dto.transaction.TransactionRequest
import com.polina.fintrackr.core.data.network.TransactionApiService
import com.polina.fintrackr.core.domain.repositories.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService
): TransactionRepository {
    override suspend fun getTransacionsForPeriod(accountId: Int, from: String, to: String) =
        api.getTransacionsForPeriod(accountId, from, to)

    override suspend fun postTransactions(transaction: TransactionRequest) =
        api.postTransactions(transaction)

    override suspend fun getTransactionById(id: Int) =
        api.getTransactionById(id)

    override suspend fun updateTransactionById(id: Int, transaction: TransactionRequest) =
        api.updateTransactionById(id, transaction)

    override suspend fun deleteTransaction(id: Int) =
        api.deleteTransaction(id)
}