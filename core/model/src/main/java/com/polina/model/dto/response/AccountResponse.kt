package com.polina.model.dto.response

import com.polina.model.dto.account.Currency
import com.polina.model.dto.account.StatItem

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
