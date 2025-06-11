package com.polina.fintrackr.core.domain

data class Category(
    val id: Int=0,
    val name: String="",
    val emoji: String="\uD83C\uDFE0",
    val isIncome: Boolean = false
)