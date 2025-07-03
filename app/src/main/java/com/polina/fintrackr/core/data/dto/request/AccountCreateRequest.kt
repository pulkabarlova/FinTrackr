package com.polina.fintrackr.core.data.dto.request

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
