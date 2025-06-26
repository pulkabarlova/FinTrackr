package com.polina.fintrackr.core.data.network

import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.account.AccountResponse
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.dto.model.transaction.Transaction
import com.polina.fintrackr.core.data.dto.transaction.TransactionRequest
import com.polina.fintrackr.core.data.dto.transaction.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApiService {
    @POST("transactions")
    suspend fun postTransactions(@Body transactions: TransactionRequest): Response<TransactionResponse>

    @GET("transactions/{id}")
    suspend fun getTransactionById(id: Int): Response<Transaction>

    @PUT("transactions/{id}")
    suspend fun updateTransactionById(
        id: Int,
        @Body transactions: TransactionRequest
    ): Response<Transaction>

    @PUT("transactions/{id}")
    suspend fun updateTransaction(id: Int, @Body account: Account): Response<AccountResponse>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(id: Int): Response<Boolean>

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransacionsForPeriod(
        @Path("accountId") accountId: Int,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<List<TransactionResponse>>
}
