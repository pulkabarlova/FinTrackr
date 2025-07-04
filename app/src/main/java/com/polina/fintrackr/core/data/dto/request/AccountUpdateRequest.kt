package com.polina.fintrackr.core.data.dto.request

import com.polina.fintrackr.core.data.dto.account.Currency

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)

