package com.polina.model.dto.response

import com.polina.model.dto.account.AccountHistory
import com.polina.model.dto.account.Currency

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistory>
)
