package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import android.util.Log
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
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

    private val startDate: Date by lazy {
        val calendar = Calendar.getInstance()
        calendar.set(2025, Calendar.JUNE, 0, 0, 0, 0)
        calendar.time
    }

    private val endDate: Date get() = Calendar.getInstance().time

    suspend fun getTransactionsForPeriod(): List<TransactionResponse> {
        if (!sharedPreferences.contains("accountId")) {
            throw AccountNotFoundException()
        }

        val accountId = sharedPreferences.getInt("accountId", -1)
        if (accountId == -1) {
            throw AccountNotFoundException()
        }

        val from = dateFormatter.format(startDate)
        val to = dateFormatter.format(endDate)
        val response = transactionRepository.getTransacionsForPeriod(accountId, from, to)

        if (!response.isSuccessful) {
            throw NetworkException()
        }

        return response.body() ?: emptyList()
    }
}