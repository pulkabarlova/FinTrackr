package com.polina.model.dto.response

import com.polina.model.dto.account.AccountBrief
import com.polina.model.dto.account.Category

data class TransactionResponse(
    val id: Int,
    val account: AccountBrief,
    val category: Category,
    val amount: Double,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)
