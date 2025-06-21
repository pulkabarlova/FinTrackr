package com.polina.fintrackr.core.data.network

import com.polina.fintrackr.core.data.dto.account.AccountCreateRequest
import com.polina.fintrackr.core.data.dto.account.AccountResponse
import com.polina.fintrackr.core.data.dto.model.account.Account
import com.polina.fintrackr.core.data.dto.model.account.Category
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("accounts/type/{isIncome}")
    suspend fun getCategoryType(isIncome: Boolean): Response<Category>
}