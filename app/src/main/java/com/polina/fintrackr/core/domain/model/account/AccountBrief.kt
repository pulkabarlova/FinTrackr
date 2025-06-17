package com.polina.fintrackr.core.domain.model.account

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
