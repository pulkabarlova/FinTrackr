package com.polina.fintrackr.core.data.dto.response

import com.polina.fintrackr.core.data.dto.account.AccountHistory

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistory>
)
