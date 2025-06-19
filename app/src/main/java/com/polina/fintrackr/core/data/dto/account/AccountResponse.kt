package com.polina.fintrackr.core.data.dto.account

import com.polina.fintrackr.core.data.dto.model.account.StatItem

data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatItem>,
    val expenseStats: List<StatItem>,
    val createdAt: String,
    val updatedAt: String
)
