package com.polina.fintrackr.core.data.dto.account

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountState?,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)
{
    enum class ChangeType {
        CREATION,
        MODIFICATION,
    }
}
