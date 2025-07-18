package com.polina.data.repositories

import android.util.Log
import com.polina.data.db.AccountDao
import com.polina.data.db.CategoryDao
import com.polina.data.db.TransactionDao
import com.polina.data.db.TransactionEntity
import com.polina.data.db.mapper.toAccount
import com.polina.data.db.mapper.toCategoryModel
import com.polina.data.db.mapper.toListTransactionEntity
import com.polina.data.db.mapper.toListTransactionResponse
import com.polina.data.db.mapper.toTransactionEntity
import com.polina.data.db.mapper.toTransactionResponse
import com.polina.model.NetworkException
import com.polina.data.network.api_service.TransactionApiService
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.domain.repositories.TransactionRepository
import com.polina.model.dto.account.AccountBrief
import com.polina.model.dto.account.Category
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

/**
 * Репозиторий для получения транзакций
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService,
    private val networkMonitor: NetworkMonitor,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao
) : TransactionRepository {
    private val dateFormatter =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
    private val formatter2 =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }

    override suspend fun getTransacionsForPeriod(
        accountId: Int,
        from: Date?,
        to: Date?
    ): List<TransactionResponse> {
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        return if (networkMonitor.isConnected()) {
            val fromFormatted = from?.let {
                dateFormatter.format(it)
            } ?: run {
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                dateFormatter.format(calendar.time)
            }

            val toFormatted = to?.let {
                dateFormatter.format(it)
            } ?: run {
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }
                dateFormatter.format(calendar.time)
            }
            val response = withContext(Dispatchers.IO) {
                api.getTransacionsForPeriod(accountId, fromFormatted, toFormatted)
            }
            if (!response.isSuccessful) {
                throw NetworkException()
            }
            response.body() ?: emptyList()
        } else {
            val fromFormatted = from?.let {
                formatter2.format(it)
            } ?: run {
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                formatter2.format(calendar.time)
            }

            val toFormatted = to?.let {
                formatter2.format(it)
            } ?: run {
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }
                formatter2.format(calendar.time)
            }

            val list = transactionDao
                .getTransactionsForPeriod(accountId, fromFormatted, toFormatted)
                .toListTransactionResponse()
            return list

        }
    }


    override suspend fun postTransactions(transaction: TransactionRequest): Result<TransactionResponse> {

        if (!networkMonitor.isConnected()) {
            val categories = categoryDao.getAll().toCategoryModel()
            val account = accountDao.getAccount().toAccount()
            val foundCategory = categories.find { it.id == transaction.categoryId }?.name ?: ""
            val foundEmoji = categories.find { it.id == transaction.categoryId }?.emoji ?: ""
            val foundIsIncome =
                categories.find { it.id == transaction.categoryId }?.isIncome ?: true
            val formattedDate = formatter2.format(Date())
            val transactionToInsert = TransactionEntity(
                id = -System.currentTimeMillis().toInt(),
                accountId = account.id,
                accountName = account.name,
                accountBalance = account.balance,
                accountCurrency = account.currency,
                categoryId = transaction.categoryId,
                categoryName = foundCategory,
                categoryEmoji = foundEmoji,
                isCategoryIncome = foundIsIncome,
                amount = transaction.amount.toDouble(),
                transactionDate = transaction.transactionDate,
                comment = transaction.comment,
                createdAt = formattedDate,
                updatedAt = formattedDate,
                isSynced = false,
                isAddedLocally = true
            )
            transactionDao.insert(transactionToInsert)
            Log.i("getTransacionsForPeriod", transactionToInsert.toString())
            return Result.success(transactionToInsert.toTransactionResponse())
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
            return Result.success(transactionDao.getTransactionById(id).toTransactionResponse())
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
            val categories = categoryDao.getAll().toCategoryModel()
            val account = accountDao.getAccount().toAccount()
            val foundCategory = categories.find { it.id == transaction.categoryId }?.name ?: ""
            val foundEmoji = categories.find { it.id == transaction.categoryId }?.emoji ?: ""
            val foundIsIncome =
                categories.find { it.id == transaction.categoryId }?.isIncome ?: true
            val formattedDate = formatter2.format(Date())
            val transactionToInsert = TransactionEntity(
                id = id,
                accountId = account.id,
                accountName = account.name,
                accountBalance = account.balance,
                accountCurrency = account.currency,
                categoryId = transaction.categoryId,
                categoryName = foundCategory,
                categoryEmoji = foundEmoji,
                isCategoryIncome = foundIsIncome,
                amount = transaction.amount.toDouble(),
                transactionDate = transaction.transactionDate,
                comment = transaction.comment,
                createdAt = formattedDate,
                updatedAt = formattedDate,
                isSynced = false
            )
            transactionDao.insert(transactionToInsert)
            return Result.success(transactionToInsert.toTransactionResponse())
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
            transactionDao.markAsDeleted(id)
            Result.success(true)
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

    override suspend fun syncTransactions() {
        val transactionToDelete = transactionDao.getPendingDeletions()
        val transactionToEdit = transactionDao.getPendingEdited()
        for (transaction in transactionToEdit) {
            if (networkMonitor.isConnected()) {
                if (transaction.isAddedLocally) {
                    val response = postTransactions(
                        TransactionRequest(
                            transaction.accountId,
                            transaction.categoryId,
                            transaction.amount.toString(),
                            transaction.transactionDate,
                            transaction.comment
                        )
                    )
                    response.fold(
                        onSuccess = { transactionResp ->
                            transactionDao.deleteById(transaction.id)
                            transactionDao.insert(
                                transaction.copy(
                                    id = transactionResp.id,
                                    isSynced = true,
                                    isAddedLocally = false
                                )
                            )
                        },
                        onFailure = {})
                } else if (transaction.isSynced == false) {
                    val response = updateTransactionById(
                        transaction.id,
                        TransactionRequest(
                            transaction.accountId,
                            transaction.categoryId,
                            transaction.amount.toString(),
                            transaction.transactionDate,
                            transaction.comment
                        )
                    )
                    response.fold(
                        onSuccess = { transactionResp ->
                            transactionDao.deleteById(transactionResp.id)
                            transactionDao.insert(
                                transaction.copy(
                                    id = transactionResp.id,
                                    isSynced = true,
                                    isAddedLocally = false
                                )
                            )
                        },
                        onFailure = {})
                }
            }
        }
        for (transaction in transactionToDelete) {
            if (networkMonitor.isConnected()) {
                val response = deleteTransaction(transaction.id)
                response.fold(
                    onSuccess = {
                        transactionDao.deleteById(transaction.id)
                    },
                    onFailure = {
                    }
                )
            }
        }
        val localTransactions = transactionDao.getAll().associateBy { it.id }
        val startOf2025 = Calendar.getInstance().apply {
            set(2025, Calendar.JANUARY, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val today = Date()
        val remoteTransactions = getTransacionsForPeriod(
            accountDao.getAccount().id,
            startOf2025,
            today
        ).toListTransactionEntity()

        for (remote in remoteTransactions) {
            val local = localTransactions[remote.id]
            if (local == null) {
                transactionDao.insert(remote)
            } else if (local.isSynced && !local.isDeleted && !local.isAddedLocally) {
                transactionDao.insert(remote)
            }
        }
    }
}