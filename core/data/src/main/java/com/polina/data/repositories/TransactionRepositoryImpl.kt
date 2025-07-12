package com.polina.data.repositories

import android.util.Log
import com.polina.model.NetworkException
import com.polina.data.network.api_service.TransactionApiService
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import com.polina.model.dto.transaction.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

/**
 * Репозиторий для получения транзакций
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService,
    private val networkMonitor: NetworkMonitor
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

    override suspend fun postTransactions(transaction: TransactionRequest): Result<TransactionResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }
        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.postTransactions(transaction) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception("Нет подключения к интернету"))
            }
        }
        return Result.failure(Exception("Нет подключения к интернету"))
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }

        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.getTransactionById(id) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception("Ошибка получения транзакции"))
            }
        }
        return Result.failure(Exception("Ошибка получения транзакции"))
    }

    override suspend fun updateTransactionById(id: Int, transaction: TransactionRequest): Result<TransactionResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }

        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.updateTransactionById(id, transaction) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception("Ошибка обновления транзакции"))
            }
        }
        return Result.failure(Exception("Ошибка обновления транзакции"))
    }

    override suspend fun deleteTransaction(id: Int): Result<Boolean>  {
        if (!networkMonitor.isConnected()) {
            return Result.failure(Exception("Нет подключения к интернету"))
        }

        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.deleteTransaction(id) }
            if (response.isSuccessful) {
                return Result.success(true)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(Exception("Ошибка удаления транзакции"))
            }
        }
        return Result.failure(Exception("Ошибка удаления транзакции"))
    }
}