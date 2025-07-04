package com.polina.fintrackr.core.data.network.api_service

import com.polina.fintrackr.core.data.dto.request.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.response.AccountResponse
import com.polina.fintrackr.core.data.dto.account.Account
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Интерфейс для работы с аккаунтами
 */

interface AccountApiService {
    @GET("accounts")
    suspend fun getAccounts(): Response<List<Account>>

    @POST("accounts")
    suspend fun createAccount(@Body account: AccountCreateRequest): Response<AccountResponse>

    @GET("accounts/{id}")
    suspend fun getAccountById(id: Int):  Response<AccountResponse>

    @PUT("accounts/{id}")
    suspend fun updateAccount(@Path("id") id: Int, @Body account: AccountCreateRequest): Response<AccountResponse>

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(id: Int): Response<Boolean>
}