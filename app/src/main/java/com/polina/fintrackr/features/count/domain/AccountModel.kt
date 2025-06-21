package com.polina.fintrackr.features.count.domain

data class AccountModel(
    val id: Int=0,
    val userId: Int=0,
    val name: String="",
    val balance: String="",
    val currency: String="",
    val createdAt: String="",
    val updatedAt: String=""
)