package com.polina.model

import com.polina.ui.models.ExpenseModel
import com.polina.ui.models.IncomeModel

/**
 * Собирает данные для отображения в рассходах/доходах
 */
data class GroupedTransactionsResult(
    val incomes: List<IncomeModel>,
    val totalIncomes: Double,
    val expenses: List<ExpenseModel>,
    val totalExpenses: Double
)