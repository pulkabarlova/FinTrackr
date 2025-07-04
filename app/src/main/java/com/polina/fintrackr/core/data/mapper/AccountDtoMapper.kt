package com.polina.fintrackr.core.data.mapper

import com.polina.fintrackr.core.data.dto.account.Account
import com.polina.fintrackr.core.data.dto.account.Currency
import com.polina.fintrackr.features.count.domain.AccountModel
/**
 * Маппинг из Account (сеть) в AccountModel(ui)
 */
fun Account.toAccountModel(): AccountModel {
    val currencySymbol = Currency.fromCode(this.currency)?.symbol ?: this.currency
    return AccountModel(
        id = this.id,
        userId = this.userId,
        name = this.name,
        balance = this.balance,
        currency = currencySymbol,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}