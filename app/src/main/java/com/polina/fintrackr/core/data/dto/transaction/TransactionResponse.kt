package com.polina.fintrackr.core.data.dto.transaction

import com.polina.fintrackr.core.data.dto.model.account.AccountBrief
import com.polina.fintrackr.core.data.dto.model.category.Category

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
