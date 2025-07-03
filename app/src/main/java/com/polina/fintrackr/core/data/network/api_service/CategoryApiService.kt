package com.polina.fintrackr.core.data.network.api_service

import com.polina.fintrackr.core.data.dto.account.Category
import retrofit2.Response
import retrofit2.http.GET

/**
 * Интерфейс для работы с категориями
 */

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("accounts/type/{isIncome}")
    suspend fun getCategoryType(isIncome: Boolean): Response<Category>
}