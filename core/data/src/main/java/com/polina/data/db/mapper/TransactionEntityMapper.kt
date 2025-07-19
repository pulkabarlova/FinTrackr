package com.polina.data.db.mapper

import com.polina.data.db.TransactionEntity
import com.polina.model.dto.account.AccountBrief
import com.polina.model.dto.account.Category
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.TransactionResponse


fun TransactionEntity.toTransactionResponse(): TransactionResponse {
    return TransactionResponse(
        id = this.id,
        account = AccountBrief(
            this.accountId,
            this.accountName,
            this.accountBalance,
            this.accountCurrency
        ),
        category = Category(
            this.categoryId,
            this.categoryName,
            this.categoryEmoji,
            this.isCategoryIncome
        ),
        amount = this.amount,
        transactionDate = this.transactionDate,
        comment = this.comment,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
fun List<TransactionEntity>.toListTransactionResponse(): List<TransactionResponse> {
    return this.map { it.toTransactionResponse() }
}

fun TransactionResponse.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        accountId = this.account.id,
        accountName = this.account.name,
        accountBalance = this.account.balance,
        accountCurrency = this.account.currency,
        categoryId = this.category.id,
        categoryName = this.category.name,
        categoryEmoji = this.category.emoji,
        isCategoryIncome = this.category.isIncome,
        amount = this.amount,
        transactionDate = this.transactionDate,
        comment = this.comment,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
fun List<TransactionResponse>.toListTransactionEntity(): List<TransactionEntity> {
    return this.map { it.toTransactionEntity() }
}
