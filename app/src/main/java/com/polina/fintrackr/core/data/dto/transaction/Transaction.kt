package com.polina.fintrackr.core.data.dto.transaction

import com.polina.fintrackr.core.data.dto.category.Category

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