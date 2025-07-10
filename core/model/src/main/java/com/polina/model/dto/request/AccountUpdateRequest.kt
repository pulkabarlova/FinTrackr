package com.polina.model.dto.request


data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)

