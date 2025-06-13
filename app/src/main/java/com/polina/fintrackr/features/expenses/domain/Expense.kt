package com.polina.fintrackr.features.expenses.domain

import java.time.LocalDateTime

data class Expense(
    val id: Int = 0,
    val title: String = "",
    val subtitle: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val currency: String = "₽",
    val trailText: String = "",
    val iconTag: String = ""
)