package com.polina.model.mapper

import com.polina.model.dto.account.Currency
import com.polina.model.dto.response.TransactionResponse
import com.polina.ui.models.IncomeModel

/**
 * Маппинг из TransactionResponse (сеть) в IncomeModel(ui)
 */

fun TransactionResponse.toIncomeModel(): IncomeModel {
    val currencySymbol = Currency.fromCode(this.account.currency)?.symbol ?: this.account.currency
    return IncomeModel(
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