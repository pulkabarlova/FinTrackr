package com.polina.fintrackr.core.data.dto.response

import com.polina.fintrackr.core.data.dto.account.AccountBrief
import com.polina.fintrackr.core.data.dto.category.Category

data class TransactionResponse(
    val id: Int,
    val account: AccountBrief,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)
