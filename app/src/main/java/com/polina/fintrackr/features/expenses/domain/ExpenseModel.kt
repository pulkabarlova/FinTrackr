package com.polina.fintrackr.features.expenses.domain

data class ExpenseModel(
    val id: Int=0,
    val title: String="",
    val subtitle: String="",
    val createdAt: Long=0,
    val currency: String="",
    val trailText: String="",
    val iconTag: String=""
)