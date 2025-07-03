package com.polina.fintrackr.core.data.dto.request

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)

