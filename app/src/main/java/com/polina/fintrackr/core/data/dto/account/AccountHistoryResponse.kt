package com.polina.fintrackr.core.data.dto.account

import com.polina.fintrackr.core.data.dto.model.account.AccountHistory

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistory>
)
