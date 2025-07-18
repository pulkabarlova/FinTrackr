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
        "SELECT * FROM transactions WHERE transactionDate BETWEEN :from AND :to AND accountId = :accountId AND isDeleted = 0"
    )
    suspend fun getTransactionsForPeriod(
        accountId: Int,
        from: String,
        to: String
    ): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): TransactionEntity

    @Query("UPDATE transactions SET isDeleted = 1, isSynced = 0 WHERE id = :id")
    suspend fun markAsDeleted(id: Int)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM transactions WHERE isSynced = 0")
    suspend fun getPendingEdited(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE isDeleted = 1 AND isSynced = 0")
    suspend fun getPendingDeletions(): List<TransactionEntity>


}