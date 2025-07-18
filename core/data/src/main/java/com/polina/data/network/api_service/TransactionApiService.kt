package com.polina.data.network.api_service

import com.polina.model.dto.account.Account
import com.polina.model.dto.request.TransactionRequest
import com.polina.model.dto.response.AccountResponse
import com.polina.model.dto.response.TransactionResponse
import com.polina.model.dto.transaction.Transaction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Интерфейс для работы с транзакциями
 */
interface TransactionApiService {
    @POST("transactions")
    suspend fun postTransactions(@Body transactions: TransactionRequest): Response<TransactionResponse>

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): Response<TransactionResponse>

    @PUT("transactions/{id}")
    suspend fun updateTransactionById(
        @Path("id") id: Int,
        @Body transactions: TransactionRequest
    ): Response<TransactionResponse>

    @PUT("transactions/{id}")
    suspend fun updateTransaction(@Path("id") id: Int, @Body account: Account): Response<AccountResponse>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Response<Boolean>

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransacionsForPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") from: String,
        @Query("endDate") to: String
    ): Response<List<TransactionResponse>>
}
