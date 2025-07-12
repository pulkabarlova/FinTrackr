package com.polina.model.mapper

import com.polina.model.dto.account.Currency
import com.polina.model.dto.response.TransactionResponse
import com.polina.ui.models.ExpenseModel

/**
 * Маппинг из TransactionResponse (сеть) в ExpenseModel(ui)
 */

fun TransactionResponse.toExpenseModel(): ExpenseModel {
    val currencySymbol = Currency.fromCode(this.account.currency)?.symbol ?: this.account.currency
    return ExpenseModel(
        id = this.id,
        title = this.category.name,
        subtitle = this.account.name,
        createdAt = this.createdAt,
        currency = currencySymbol,
        trailText = this.amount,
        emoji = this.category.emoji,
        amount = this.amount.toDouble()
    )
}