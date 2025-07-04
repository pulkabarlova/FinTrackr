package com.polina.fintrackr.core.data.dto.request

import com.polina.fintrackr.core.data.dto.account.Currency

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
