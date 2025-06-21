package com.polina.fintrackr.features.expenses.domain

data class ExpenseModel(
    val id: Int=0,
    val title: String="",
    val subtitle: String="",
    val createdAt: String="",
    val currency: String="",
    val trailText: String="",
    val emoji: String="",
    val amount: Double=0.0
)