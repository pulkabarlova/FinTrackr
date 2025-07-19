package com.polina.data.db.mapper

import com.polina.data.db.AccountEntity
import com.polina.model.dto.account.Account

fun AccountEntity.toAccount(): Account {
    return Account(
        id = this.id,
        userId = this.userId,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun Account.toAccountEntity(): AccountEntity {
    return AccountEntity(
        id = this.id,
        userId = this.userId,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}