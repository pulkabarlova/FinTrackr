package com.polina.domain.use_case

import android.content.SharedPreferences
import com.polina.model.mapper.toExpenseModel
import com.polina.model.mapper.toIncomeModel
import com.polina.domain.repositories.TransactionRepository
import com.polina.model.AccountNotFoundException
import com.polina.model.GroupedTransactionsResult
import com.polina.model.NetworkException
import com.polina.ui.models.ExpenseModel
import com.polina.ui.models.IncomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

/**
 * Получение и обработка транзакций
 */
class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val sharedPreferences: SharedPreferences,
) {

    suspend fun getTransactionsData(
        start: Date?,
        end: Date?
    ): GroupedTransactionsResult = withContext(Dispatchers.IO) {
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

        val incomes = mutableListOf<IncomeModel>()
        val expenses = mutableListOf<ExpenseModel>()
        var sumIncomes = 0.0
        var sumExpenses = 0.0
        transactions.forEach { tx ->
            if (tx.category.isIncome) {
                incomes.add(tx.toIncomeModel())
                sumIncomes += tx.amount.toFloat()
            } else {
                expenses.add(tx.toExpenseModel())
                sumExpenses += tx.amount.toFloat()
            }
        }
        return@withContext GroupedTransactionsResult(
            incomes = incomes,
            expenses = expenses,
            totalIncomes = sumIncomes,
            totalExpenses = sumExpenses
        )
    }
}
