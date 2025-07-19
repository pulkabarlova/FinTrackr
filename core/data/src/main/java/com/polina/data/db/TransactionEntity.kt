package com.polina.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: Int,
    //account
    val accountId: Int,
    val accountName: String,
    val accountBalance: String,
    val accountCurrency: String,
    //category
    val categoryId: Int,
    val categoryName: String,
    val categoryEmoji: String,
    val isCategoryIncome: Boolean,
    val amount: Double,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val isAddedLocally:Boolean = false,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false
)
