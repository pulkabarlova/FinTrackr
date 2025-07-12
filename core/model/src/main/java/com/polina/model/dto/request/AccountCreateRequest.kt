package com.polina.model.dto.request


data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
