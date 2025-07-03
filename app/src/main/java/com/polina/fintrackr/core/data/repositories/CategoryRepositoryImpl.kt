package com.polina.fintrackr.core.data.repositories

import com.polina.fintrackr.core.data.mapper.toCategoryModel
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.network.api_service.CategoryApiService
import com.polina.fintrackr.core.domain.repositories.CategoryRepository
import com.polina.fintrackr.features.articles.domain.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * Репощиторий для получения категорий
 */
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApiService
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryModel> {
        return try {
            val response = withContext(Dispatchers.IO) { api.getCategories() }
            if (response.isSuccessful) {
                response.body()?.toCategoryModel() ?: emptyList()
            } else {
                throw NetworkException()
            }
        } catch (e: Exception) {
            throw NetworkException()
        }
    }

    override suspend fun getCategoryType(isIncome: Boolean) = api.getCategoryType(isIncome)
}