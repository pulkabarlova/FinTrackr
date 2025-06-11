package com.polina.fintrackr.features.articles.domain

data class Article(
    val id: Int=0,
    val name: String="",
    val emoji: String="\uD83C\uDFE0",
    val isIncome: Boolean = false
)