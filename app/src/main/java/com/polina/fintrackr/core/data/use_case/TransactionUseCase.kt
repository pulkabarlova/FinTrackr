package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import android.util.Log
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import com.polina.fintrackr.core.data.repositories.TransactionRepository
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

    // Начальная дата - 19.06.2025
    private val startDate: Date by lazy {
        val calendar = Calendar.getInstance()
        calendar.set(2025, Calendar.JUNE, 0, 0, 0, 0)
        calendar.time
    }

    // Конечная дата - текущая дата
    private val endDate: Date get() = Calendar.getInstance().time

    suspend fun getTransactionsForPeriod(): List<TransactionResponse>? {
        if (sharedPreferences.contains("accountId")) {
            val accountId = sharedPreferences.getInt("accountId", -1)
            Log.d("Sharedpr", "response: $accountId")
            if (accountId != -1) {
                val from = dateFormatter.format(startDate)
                val to = dateFormatter.format(endDate)

                val response = transactionRepository.getTransacionsForPeriod(accountId, from, to)
                Log.d("Sharedpr", "response: $response")
                if (response.isSuccessful) {
                    val transactions = response.body()

                    return transactions
                }
            }
        }
        return null
    }
}