package com.polina.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionEntity>

    @Query(
        "SELECT * FROM transactions WHERE transactionDate BETWEEN :from AND :to AND accountId = :accountId"
    )
    suspend fun getTransactionsForPeriod(
        accountId: Int,
        from: String,
        to: String
    ): List<TransactionEntity>

}