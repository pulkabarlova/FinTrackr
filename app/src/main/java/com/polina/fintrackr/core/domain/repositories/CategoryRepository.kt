package com.polina.fintrackr.core.domain.repositories

import com.polina.fintrackr.core.data.dto.model.account.Category
import com.polina.fintrackr.features.articles.domain.CategoryModel
import retrofit2.Response
/**
 * Репозиторий для получения категорий
 */
interface CategoryRepository {
    suspend fun getCategories(): List<CategoryModel>
    suspend fun getCategoryType(isIncome: Boolean): Response<Category>
}
