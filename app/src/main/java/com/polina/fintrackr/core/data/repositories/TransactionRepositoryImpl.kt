package com.polina.fintrackr.core.data.repositories

import android.util.Log
import com.polina.fintrackr.core.data.dto.request.TransactionRequest
import com.polina.fintrackr.core.data.dto.response.TransactionResponse
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.network.api_service.TransactionApiService
import com.polina.fintrackr.core.domain.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
/**
 * Репозиторий для получения транзакций
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService
) : TransactionRepository {
    private val dateFormatter =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

    override suspend fun getTransacionsForPeriod(
        accountId: Int,
        from: String?,
        to: String?
    ): List<TransactionResponse> {
        val fromDate = from ?: Calendar.getInstance().apply {
            set(2025, Calendar.JUNE, 1, 0, 0, 0)
        }.time
        val toDate = to ?: Calendar.getInstance().time
        val from = dateFormatter.format(fromDate)
        val to = dateFormatter.format(toDate)
        val response =
            withContext(Dispatchers.IO) { api.getTransacionsForPeriod(accountId, from, to) }

        if (!response.isSuccessful) {
            throw NetworkException()
        }
        Log.d("TransactionRepositoryImpl", "getTransacionsForPeriod: $response")
        return response.body() ?: emptyList()
    }

    override suspend fun postTransactions(transaction: TransactionRequest) =
        api.postTransactions(transaction)

    override suspend fun getTransactionById(id: Int) =
        api.getTransactionById(id)

    override suspend fun updateTransactionById(id: Int, transaction: TransactionRequest) =
        api.updateTransactionById(id, transaction)

    override suspend fun deleteTransaction(id: Int) =
        api.deleteTransaction(id)
}