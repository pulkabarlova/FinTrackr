package com.polina.fintrackr.core.data.dto.account

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)

