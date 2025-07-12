package com.polina.ui.models
/**
 * Класс для отображения в ui
 */
data class CategoryModel(
    val id: Int=0,
    val name: String="",
    val emoji: String="",
    val isIncome: Boolean=false
)