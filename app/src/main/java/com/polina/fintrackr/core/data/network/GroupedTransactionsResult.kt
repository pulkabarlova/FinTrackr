package com.polina.fintrackr.core.data.network

import com.polina.fintrackr.features.expenses.domain.ExpenseModel
import com.polina.fintrackr.features.incoms.domain.IncomeModel

/**
 * Собирает данные для отображения в рассходах/доходах
 */
data class GroupedTransactionsResult(
    val incomes: List<IncomeModel>,
    val totalIncomes: Double,
    val expenses: List<ExpenseModel>,
    val totalExpenses: Double
)