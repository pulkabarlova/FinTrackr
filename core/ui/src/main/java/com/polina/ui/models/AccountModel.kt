package com.polina.ui.models

/**
 * Класс для отображения в ui
 */
data class AccountModel(
    val id: Int=0,
    val userId: Int=0,
    val name: String="",
    val balance: String="",
    val currency: String="",
    val createdAt: String="",
    val updatedAt: String=""
)