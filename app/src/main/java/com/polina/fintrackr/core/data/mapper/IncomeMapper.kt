package com.polina.fintrackr.core.data.mapper

import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import com.polina.fintrackr.features.expenses.domain.ExpenseModel
import com.polina.fintrackr.features.incoms.domain.IncomeModel
/**
 * Маппинг из TransactionResponse (сеть) в IncomeModel(ui)
 */

fun TransactionResponse.toIncomeModel(): IncomeModel {
    return IncomeModel(
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