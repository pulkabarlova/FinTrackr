package com.polina.fintrackr.core.data.mapper

import com.polina.fintrackr.core.data.dto.model.transaction.Transaction
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import com.polina.fintrackr.features.expenses.domain.ExpenseModel
/**
 * Маппинг из TransactionResponse (сеть) в ExpenseModel(ui)
 */

fun TransactionResponse.toExpenseModel(): ExpenseModel {
    return ExpenseModel(
        id = this.id,
        title = this.category.name,
        subtitle = this.account.name,
        createdAt = this.createdAt,
        currency = this.account.currency,
        trailText = this.amount,
        emoji = this.category.emoji,
        amount = this.amount.toDouble()
    )
}