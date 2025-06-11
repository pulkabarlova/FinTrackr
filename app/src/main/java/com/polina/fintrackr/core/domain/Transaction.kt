package com.polina.fintrackr.core.domain

data class Transaction(
    val id: Int,
    val account: String="",
    val category: Category,
    val amount: Int,
    val transactionDate: String="",
    val comment: String? = null,
    val createdAt: String="",
    val updatedAt: String=""
)