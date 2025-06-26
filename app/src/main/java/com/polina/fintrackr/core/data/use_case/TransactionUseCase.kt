package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import android.util.Log
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.domain.repositories.TransactionRepository
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val sharedPreferences: SharedPreferences
) {
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    suspend fun getTransactionsForPeriodWithRetries(start: Date?, end: Date?): List<TransactionResponse> {
        repeat(3) { attempt ->
            try {
                return getTransactionsForPeriod(start, end)
            } catch (e: NetworkException) {
                if (attempt < 2) delay(5000)
                else throw e
            } catch (e: Exception) {
                throw e
            }
        }
        return emptyList()
    }

    private suspend fun getTransactionsForPeriod(start: Date?, end: Date?): List<TransactionResponse> {
        if (!sharedPreferences.contains("accountId")) {
            throw AccountNotFoundException()
        }
        val fromDate = start ?: Calendar.getInstance().apply {
            set(2025, Calendar.JUNE, 1, 0, 0, 0)
        }.time

        val toDate = end ?: Calendar.getInstance().time

        val accountId = sharedPreferences.getInt("accountId", -1)
        if (accountId == -1) {
            throw AccountNotFoundException()
        }

        val from = dateFormatter.format(fromDate)
        val to = dateFormatter.format(toDate)
        val response = transactionRepository.getTransacionsForPeriod(accountId, from, to)

        if (!response.isSuccessful) {
            throw NetworkException()
        }

        return response.body() ?: emptyList()
    }
}
