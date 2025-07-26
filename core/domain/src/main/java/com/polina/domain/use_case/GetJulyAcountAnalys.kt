package com.polina.domain.use_case

import android.content.SharedPreferences
import com.polina.domain.repositories.TransactionRepository
import com.polina.model.AccountNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetJulyAcountAnalys @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val sharedPreferences: SharedPreferences,
) {

    suspend fun getIncomeMinusExpenses(
        start: Date?,
        end: Date?
    ): Double = withContext(Dispatchers.IO) {
        if (!sharedPreferences.contains("accountId")) {
            throw AccountNotFoundException()
        }

        val accountId = sharedPreferences.getInt("accountId", -1)
        if (accountId == -1) {
            throw AccountNotFoundException()
        }

        val transactions = transactionRepository.getTransacionsForPeriod(
            accountId = accountId,
            from = start,
            to = end
        )

        val incomeSum = transactions
            .filter { it.category.isIncome }
            .sumOf { it.amount }

        val expenseSum = transactions
            .filter { !it.category.isIncome }
            .sumOf { it.amount }

        return@withContext incomeSum - expenseSum
    }
}