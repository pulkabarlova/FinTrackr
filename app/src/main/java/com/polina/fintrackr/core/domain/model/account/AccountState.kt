package com.polina.fintrackr.core.domain.model.account

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
