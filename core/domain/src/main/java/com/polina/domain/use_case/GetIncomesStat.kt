package com.polina.domain.use_case

import android.content.SharedPreferences
import com.polina.domain.repositories.TransactionRepository
import com.polina.model.AccountNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

data class IncomeCategorySummary(
    val categoryName: String,
    val amount: Double,
    val percentage: Int,
    val icon: String? = null,
)

data class CategorizedIncomeResult(
    val totalAmount: Double,
    val categories: List<IncomeCategorySummary>
)

class GetIncomesStat @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val sharedPreferences: SharedPreferences,
) {

    suspend fun getCategorizedTransactions(
        start: Date?,
        end: Date?
    ): CategorizedIncomeResult = withContext(Dispatchers.IO) {
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

        val incomeTransactions = transactions.filter { it.category.isIncome }

        val totalAmount = incomeTransactions.sumOf { it.amount }

        val grouped = incomeTransactions.groupBy { it.category }

        val categorySummaries = grouped.map { (category, txList) ->
            val sum = txList.sumOf { it.amount }
            val percent = if (totalAmount > 0) ((sum / totalAmount) * 100).toInt() else 0

            IncomeCategorySummary(
                categoryName = category.name,
                amount = sum,
                percentage = percent,
                icon = category.emoji
            )
        }.sortedByDescending { it.percentage }

        return@withContext CategorizedIncomeResult(
            totalAmount = totalAmount,
            categories = categorySummaries
        )
    }
}