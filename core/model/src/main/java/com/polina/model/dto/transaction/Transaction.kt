package com.polina.model.dto.transaction

import com.polina.model.dto.account.Category

data class Transaction(
    val id: Int,
    val account: String,
    val category: Category,
    val amount: Int,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)