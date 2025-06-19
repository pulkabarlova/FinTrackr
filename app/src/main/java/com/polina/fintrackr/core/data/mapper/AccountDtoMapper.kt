package com.polina.fintrackr.core.data.mapper

import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.features.count.domain.AccountModel

fun Account.toAccountModel(): AccountModel {
    return AccountModel(
        id = this.id,
        userId = this.userId,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}