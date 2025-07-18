package com.polina.data.repositories

import com.polina.data.db.TransactionDao
import com.polina.data.db.mapper.toListTransactionEntity
import com.polina.data.db.mapper.toListTransactionResponse
import com.polina.model.NetworkException
import com.polina.data.network.api_service.TransactionApiService
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val api: TransactionApiService,
    private val networkMonitor: NetworkMonitor,
    private val transactionDao: TransactionDao
) : TransactionRepository {
    private val dateFormatter =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

    override suspend fun getTransacionsForPeriod(
        accountId: Int,
        from: String?,
        to: String?
    ): List<TransactionResponse> {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        val fromFormatted = from ?: run {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            dateFormatter.format(calendar.time)
        }

        val toFormatted = to ?: run {
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            dateFormatter.format(calendar.time)
        }

        return if (networkMonitor.isConnected()) {
            val response = withContext(Dispatchers.IO) {
                api.getTransacionsForPeriod(accountId, fromFormatted, toFormatted)
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    transactionDao.insertAll(it.toListTransactionEntity())
                }
            } else {
                throw NetworkException()
            }
            response.body() ?: emptyList()
        } else {
            transactionDao
                .getTransactionsForPeriod(accountId, fromFormatted, toFormatted)
                .toListTransactionResponse()
        }
    }


    override suspend fun postTransactions(transaction: TransactionRequest): Result<TransactionResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(NetworkException())
        }
        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.postTransactions(transaction) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(NetworkException())
            }
        }
        return Result.failure(Exception())
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(NetworkException())
        }

        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.getTransactionById(id) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(NetworkException())
            }
        }
        return Result.failure(Exception())
    }

    override suspend fun updateTransactionById(
        id: Int,
        transaction: TransactionRequest
    ): Result<TransactionResponse> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(NetworkException())
        }

        repeat(3) { attempt ->
            val response =
                withContext(Dispatchers.IO) { api.updateTransactionById(id, transaction) }
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(NetworkException())
            }
        }
        return Result.failure(Exception())
    }

    override suspend fun deleteTransaction(id: Int): Result<Boolean> {
        if (!networkMonitor.isConnected()) {
            return Result.failure(NetworkException())
        }

        repeat(3) { attempt ->
            val response = withContext(Dispatchers.IO) { api.deleteTransaction(id) }
            if (response.isSuccessful) {
                return Result.success(true)
            }
            if (response.code() == 500 && attempt < 2) {
                delay(2000)
            } else {
                return Result.failure(NetworkException())
            }
        }
        return Result.failure(Exception())
    }
}