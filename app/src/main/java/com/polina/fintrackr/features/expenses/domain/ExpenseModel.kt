package com.polina.fintrackr.features.expenses.domain

import com.polina.fintrackr.core.data.dto.account.Currency

/**
 * Класс для отображения в ui
 */
data class ExpenseModel(
    val id: Int=0,
    val title: String="",
    val subtitle: String="",
    val createdAt: String="",
    val currency: String,
    val trailText: String="",
    val emoji: String="",
    val amount: Double=0.0
)