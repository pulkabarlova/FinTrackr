package com.polina.fintrackr.features.articles.domain

data class CategoryModel(
    val id: Int=0,
    val name: String="",
    val emoji: String="",
    val isIncome: Boolean=false
)