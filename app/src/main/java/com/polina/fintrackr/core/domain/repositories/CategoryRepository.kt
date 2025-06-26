package com.polina.fintrackr.core.domain.repositories

import com.polina.fintrackr.core.data.dto.model.account.Category
import retrofit2.Response

interface CategoryRepository {
    suspend fun getCategories(): Response<List<Category>>
    suspend fun getCategoryType(isIncome: Boolean): Response<Category>
}
