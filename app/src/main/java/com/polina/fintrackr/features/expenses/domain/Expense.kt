package com.polina.fintrackr.features.expenses.domain

import java.time.LocalDateTime

data class Expense(
    val id: String? = null,
    val title: String,
    val subtitle: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val currency: String = "₽",
    val trailText: String,
    val iconTag: String
)