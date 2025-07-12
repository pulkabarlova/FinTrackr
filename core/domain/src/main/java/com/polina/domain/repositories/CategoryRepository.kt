package com.polina.domain.repositories

import com.polina.model.dto.account.Category
import com.polina.ui.models.CategoryModel
import retrofit2.Response
/**
 * Репозиторий для получения категорий
 */
interface CategoryRepository {
    suspend fun getCategories(): List<CategoryModel>
    suspend fun getCategoryType(isIncome: Boolean): Response<Category>
}
